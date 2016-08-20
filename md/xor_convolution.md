# 集合畳み込み (Xor)

[Fourier Meets Möbius: Fast Subset Convolution](references/subset-conv.pdf)

$n$ 要素の集合に対する関数 $f$, $g$ に対して、関数
$$
(f \ast g)(S) = \sum_{T}f(T)g(S\Delta T)
$$
を、$O(n2^n)$ 時間で計算する。($\Delta$ は xor.)

## アルゴリズム

$n=2$ の場合を例にする。 

$$
\begin{align*}
(f\ast g)(00) = f(00)g(00) + f(01)g(01) + f(10)g(10) + f(11)g(11) \\
(f\ast g)(01) = f(00)g(01) + f(01)g(00) + f(10)g(11) + f(11)g(10) \\
(f\ast g)(10) = f(00)g(10) + f(01)g(11) + f(10)g(00) + f(11)g(01) \\
(f\ast g)(11) = f(00)g(11) + f(01)g(01) + f(10)g(01) + f(11)g(00) \\
\end{align*}
$$

$f_0(S) = f(0S), f_1(S) = f(1S)$ のようにおくと、

$$
\begin{align*}
(f\ast g)(00) = (f_0\ast g_0)(0) + (f_1\ast g_1)(0) \\
(f\ast g)(01) = (f_0\ast g_0)(1) + (f_1\ast g_1)(1) \\
(f\ast g)(10) = (f_0\ast g_1)(0) + (f_1\ast g_0)(0) \\
(f\ast g)(11) = (f_0\ast g_1)(1) + (f_1\ast g_0)(1) \\
\end{align*}
$$

となっていることがわかる。そのままだと、$\ast$ を 4 回計算しなければならないが、
$$
\begin{align*}
(f_0 + f_1)\ast(g_0 + g_1) = f_0\ast g_0 + f_0 \ast g_1 + f_1 \ast g_0 + f_1\ast g_1 \\
(f_0 - f_1)\ast(g_0 + g_1) = f_0\ast g_0 - f_0 \ast g_1 - f_1 \ast g_0 + f_1\ast g_1
\end{align*}
$$
を使うことによって、

$$
\begin{align*}
(f\ast g)(00) = ((f_0 + f_1)\ast(g_0 + g_1) + (f_0 - f_1)\ast(g_0 - g_1))(0) / 2 \\
(f\ast g)(01) = ((f_0 + f_1)\ast(g_0 + g_1) + (f_0 - f_1)\ast(g_0 - g_1))(1) / 2 \\
(f\ast g)(10) = ((f_0 + f_1)\ast(g_0 + g_1) - (f_0 - f_1)\ast(g_0 - g_1))(0) / 2 \\
(f\ast g)(11) = ((f_0 + f_1)\ast(g_0 + g_1) - (f_0 - f_1)\ast(g_0 - g_1))(1) / 2 \\
\end{align*}
$$

と、2 回の計算ですむ。このようにして、計算量 $O(n 2^n)$ が得られる。(Fast Wavelet Transform というらしい？)

## 問題集
[TopCoder SRM 518 Hard Nim](https://community.topcoder.com/stat?c=round_overview&er=5&rd=14543)

## ソースコード
https://github.com/ogiekako/contest/search?utf8=%E2%9C%93&q=xorConvolution+language%3AJava+path%3Amain%2Fnet%2Fogiekako&type=Code
