# 最小シュタイナー木

問題：$n$ 頂点 $m$ 枝のグラフに $k$ 個のターミナル頂点がある。すべてのターミナル頂点 $K$ を結ぶ木であって、枝の重みが最小のものを求めよ。

$O(3^kn + 2^km)$ 時間のアルゴリズムがよく知られている (Dreyfus-Wagner)。
Andreas Bjöuklund らによって、$O(2^kn^2 + nm)$ 時間で解けることが示された。([Fourier Meets Mobius: Fast Subset Convolution](http://web.stanford.edu/~rrwill/presentations/subset-conv.pdf))

## $O^*(3^k)$ のアルゴリズム
$K$ をターミナル頂点全体とする。
頂点 $v$ とターミナル集合 $S$ を連結にする最小の木の重さを $\mathrm{dp}[S][v]$ とする。

<img src="https://docs.google.com/drawings/d/1df6capV8pwv3RbQ6-jieZY96J8_bN9EZ-Ci7MYSGseU/pub?w=238&amp;h=156">

$$
\mathrm{dp}[S][v] = \min\left(
\min_{vu\in E} \mathrm{dp}[S][u] + 1,
\min_{T\subseteq S} \mathrm{dp}[S\setminus T][v] + \mathrm{dp}[T][v]
\right)
$$

となる。状態にループがあるので、$\forall_{v\in K} \mathrm{dp}[\{v\}][v]=0$ と初期化してからダイクストラ（重みなしの場合は幅優先）で小さい方から更新していく。
$\mathrm{dp}[S][v]$ からは、$\mathrm{dp}[S][u]\ \forall uv\in E$ と $\mathrm{dp}[S\cup T][v]\ \forall_{T\subseteq K\setminus S}$ に枝が伸びている。求める値は $\min_{v\in V}\mathrm{dp}[K][v]$  である。

## 集合の畳み込み
集合を $N$ としたとき、$S\subseteq N$ に対して、
て 
$$
(f * g)(S) = \sum_{T\subseteq S}f(T)g(S\setminus T)
$$
と定義する。これを $O(n^22^n)$ 時間で求められる。
