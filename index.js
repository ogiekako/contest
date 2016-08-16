// Copied from http://qiita.com/DUxCA/items/27b7b865a0ab28b5d530.
window.addEventListener("DOMContentLoaded", main);
window.addEventListener("hashchange", main);

var get = $.get;
var renderer = new marked.Renderer();
renderer.code = function(code, language) {
  return '<pre'+'><code class="hljs">' + hljs.highlightAuto(code).value + '</code></pre>';
};
marked.setOptions({
  renderer: renderer,
  gfm: true,
  tables: true,
  // 改行を無視しない
  breaks: true,
   smartLists: false,
  langPrefix: '',
});

// $$ 内の文字を escape して、marked が処理しないようにする。
function escape(str) {
  return str.replace(new RegExp("\n", 'g'), "<CR>");
}
function unescape(str) {
  return str.replace(new RegExp("<CR>", 'g'), "\n");
}

function main(){
  // ルーティング
  var name = "";
  if( location.hash.length <= 1 ){
    name = "index.md";
    location.hash = "#" + name;
  }else{
    name = location.hash.slice(1);
  }
  // ページ内リンクなのでhistory.pushStateする必要はない
  get(name).catch(function(){
    return Promise.resolve("# 404 page not found");
  }).then(function(text){
    // markedにlatexタグ食わせると"&<>"とかがエスケープされるのでコメントアウトして退避。
    // 先頭の a は、段落の最初に $ があっても段落全体が<p>で囲まれるようにするため。
    var PREFIX_LARGE = "a<!--lang-math-large==";
    var PREFIX_SMALL = "a<!--lang-math-small==";
    var SUFFIX = "-->";
    var regLarge = new RegExp(
      ("(?:" + PREFIX_LARGE + "([\\s\\S]*?)" + SUFFIX + ")").replace(/\//g, "\/"),
      "gm");
    var regSmall = new RegExp(
      ("(?:" + PREFIX_SMALL + "([\\s\\S]*?)" + SUFFIX + ")")
        .replace(/\//g, "\/"),
      "gm");
    var wrapped1 = text.split("$$")
      .reduce(function(sum, str, i){
        return i % 2 === 0 ?
               sum + str :
               sum + PREFIX_LARGE + escape(str) + SUFFIX; // TODO: escape |str| for the case where it contains -->.
      }, "");
    var wrapped = wrapped1.split("$")
      .reduce(function(sum, str, i){
        return i % 2 === 0 ?
               sum + str :
               sum + PREFIX_SMALL + escape(str) + SUFFIX;
      }, "");
    var html = marked(wrapped);

    // 退避したlatexタグを$$で包み直す
    var _html = html;
    var tuple = null;
    while(tuple = regLarge.exec(html)){
      _html = _html.split(tuple[0]).join("$$" + unescape(tuple[1]) + "$$");
    }
    while(tuple = regSmall.exec(html)){
      _html = _html.split(tuple[0]).join("$" + unescape(tuple[1]) + "$");
    }
    // mathjaxで処理
    var div = document.getElementById("content");
    div.innerHTML = _html;
    MathJax.Hub.Configured();
    MathJax.Hub.Queue(["Typeset", MathJax.Hub, div]);
  }).catch(function(err){
    // jqueryのpromiseはthenの中でエラー吐いて止まってもconsoleに表示してくれないので表示させる
    console.error(err);
  });
}
