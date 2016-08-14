# カタラン数

https://en.wikipedia.org/wiki/Catalan_number

$n$ 番目のカタラン数 $C_n$ は、
$n$ 個のカッコ対をバランスして書く方法のこと。または対角線を超えないように上か右に行って、$(n,n)$ に到達する方法の数。$C_n = \binom{2n}n - \binom{2n}{n+1}$ が成立する。
証明：ダメなパスでは、$(0,1), (n,n+1)$ を結ぶ線(fatal diagonal)に触ることになる。最初に触ったところで折り返すと、$(n-1,n+1)$ に到達するパスになることがわかる。逆に、$(n-1,n+1)$に到達するパスは必ず、fatal diagonal に触れるので、ダメなパスと、$(n-1,n+1)$に到達するパスの間には一対一対応がある。よって、すべてのパス$\binom{2n}n$からダメなパス$\binom{2n}{n+1}$を引けばカタラン数となる。([引用元](https://en.wikipedia.org/wiki/Catalan_number#Second_proof))

## Catalan's Triangle

https://en.wikipedia.org/wiki/Catalan%27s_triangle

$C(n,k)$ を、対角線を超えずに、右に $n$ 回、上に $k$ 回進み $(n,k)$ に到達する方法の数。$C(n,n) = C_n$ である。
例: $C(2,1) = 2$
```text
        _
__|   _|
```

カタラン数の場合と同様にして、$C(n,k) = \binom{n+k}{n} - \binom{n+k}{n+1}$ となることがわかる。

### 逆転バージョン

https://oeis.org/A033184

$T(n,k)$ を、対角線に、最後も含めてちょうど $k$ 回ぶつかるカタランパスの数とする。(特に標準的な記法はなさそう)
$T(0,0) = 1$ と定義する。

例: $T(3,1) = 2$.
```text
    |     |
   _|     |
__|    ___|
```

$T(3,2) = 2$.
```text
    |     _|
  __|    |
_|     __|
```

実は、$n>0$ で、$T(n,k) = C(n-1,n-k)$ が成り立つ。
証明: $k$ 回対角線に触れる$n$―カタランパスは、$k-1$ 回 fatal diagonal に触れる $(n-1,n-1)$-単調パスと対応する($n$ 回右に、$k$ 回上に行くパスを $(n,k)$-単調パス と書くことにした)。
$x$ 回 fatal diagonal に触れる $(n,k)$-単調パスは、$x-1$ 回 fatal diagonal に触れる $(n,k-1)$-単調パスと一対一対応を持つ。具体的には、最後に fatal diagonal に触れることになった上向きの矢印を削除すれば良い。逆方向は、最後に触れた対角線からの右への移動の前に上への移動を追加すれば良い。よって、$k-1$ 回 fatal diagonal に触れる $(n-1,n-1)$-単調パスの総数は、$(n-1,n-k)$-カタランパス の総数に等しく、それは $C(n-1, n-k)$ である。

## Source code
https://github.com/ogiekako/contest/search?utf8=%E2%9C%93&q=catalan+language%3AJava+path%3Amain%2Fnet%2Fogiekako&type=Code
