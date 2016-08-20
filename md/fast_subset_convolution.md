# Fast Subset Convolution

[Fourier Meets Möbius: Fast Subset Convolution](references/subset-conv.pdf)

$n$ 要素の集合に対する関数 $f$, $g$ に対して、関数
$$
(f \ast g)(S) = \sum_{T\subseteq S}f(T)g(S\setminus T)
$$
を、$O(n^22^n)$ 時間で計算する。実用的には、$O(3^n)$ でやったほうが速い。

## アルゴリズム

$$
\hat{f}(k, X) = \sum_{S\subseteq X, |S|=k}f(S)
$$
とおく。 (例: $\hat{f}(1, 11) = f(01) + f(10)$).  これは、各 $k$ について高速メビウス変換することによって、$O(n^22^n)$ 時間で求められる。

$$
\hat{h}(k,X) = (\hat{f}\star \hat{g})(k,X) = \sum_{j=0}^k\hat{f}(j,X)\hat{g}(k-j,X)
$$
と定義する。

例:
$$
\begin{align*}
\hat{h}(2,11) & = f(00)g(11)                     \\
              & + (f(01) + f(10))(g(01) + g(10)) \\
              & + f(11)g(00)                     \\
\hat{h}(2,01) & = f(01)g(01)                     \\
\hat{h}(2,10) & = f(10)g(10)                     \\
\end{align*}
$$

すると
$$
(f\ast g)(S) = \sum_{X\subseteq S}(-1)^{|S\setminus X|}\hat{h}(|S|, X)
$$
となることがわかる。(例: $(f\ast g)(11) = \hat{h}(2, 11) - \hat{h}(2, 01) - \hat{h}(2, 10)$
$$
\begin{array}{c|cccc}
   & 00 & 01 & 10 & 11\\
\hline
00 &    &    &    & 11\\
01 &    & 11 - 01 & 11 &   \\
10 &    & 11 & 11 - 10 &   \\
11 & 11 &    &    &   \\
\end{array}
$$
$f(01)g(01)$ の寄与は、$\hat{h}(11)$ と $\hat{h}(01)$ によって相殺されている。同様にして、$f(U)g(V)$ の $S$ に対する寄与は、$S$ をぴったり覆っていない場合は、覆っていない要素の不定性によって、プラスマイナスが相殺される。)

これは、各 $|S|$ について高速メビウス逆変換することによって、$O(n^22^n)$ 時間で求められる。

以上より、$O(n^22^n)$ 時間で、畳み込みが求められた。

## ソースコード
https://github.com/ogiekako/contest/search?utf8=%E2%9C%93&q=fastSubsetConvolution+language%3AJava+path%3Amain%2Fnet%2Fogiekako&type=Code
