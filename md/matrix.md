# 線形代数の基礎

## 行列

http://ocw.mit.edu/courses/mathematics/18-06-linear-algebra-spring-2010/video-lectures/lecture-3-multiplication-and-inverse-matrices/

行列の積 $AB$ は 行単位と列単位の見かたができる。$A$ の行を要素ごとに $B$ の各行にかけて新しい行を作るというのが行単位の見かた。$B$ の列を要素ごとに $A$ の各列にかけて新しい列をつくるというのが列単位の見かた。
(「左」の 3 画目が ― であり、「右」の 3 画目が | であることから左からかけるのは行の操作、右からけるのは列の操作と覚えておくと良い。)

<a href="https://gyazo.com/c7a13441570f42bc016bd2182d4cc4e2"><img src="https://i.gyazo.com/c7a13441570f42bc016bd2182d4cc4e2.gif" alt="https://gyazo.com/c7a13441570f42bc016bd2182d4cc4e2" width="300"/></a>
<a href="https://gyazo.com/8669e758a5669b511b9dd1359894eb3b"><img src="https://i.gyazo.com/8669e758a5669b511b9dd1359894eb3b.gif" alt="https://gyazo.com/8669e758a5669b511b9dd1359894eb3b" width="300"/></a>

## 行列式
http://ocw.mit.edu/courses/mathematics/18-06-linear-algebra-spring-2010/video-lectures/lecture-18-properties-of-determinants/

以下の 3 つの性質は行列式の特徴付けである。

1. $\mathrm{det}(I) = 1$
2. 2つの行を入れ替えると、行列式の符号が変わる
3. 1つの行に関する線形性が成立する。
$$
\left|
\begin{matrix}
\color{red}ta& \color{red}tb  \\
c & d
\end{matrix}
\right|
=
\color{red}t\left|
\begin{matrix}
a& b  \\
c & d
\end{matrix}
\right|
\\

\left|
\begin{matrix}
a+a'& b+b'  \\
c & d
\end{matrix}
\right|
=
\left|
\begin{matrix}
a& b  \\
c & d
\end{matrix}
\right|
+
\left|
\begin{matrix}
a'& b'  \\
c & d
\end{matrix}
\right|
$$ (補足: 下の式から
$
\left|
\begin{matrix}
a & b  \\
c & d
\end{matrix}
\right|
=
\color{gray}{
\left|
\begin{matrix}
a+0& 0+b  \\
c & d
\end{matrix}
\right|
=
}
\left|
\begin{matrix}
a& 0  \\
c & d
\end{matrix}
\right|
+
\left|
\begin{matrix}
0 & b  \\
c & d
\end{matrix}
\right|
$
を得る。これを第2列についても繰り返せば、Permutation を使った行列式の定義を得る。)
<br>
以下は、1-3 から導かれる性質:
4. 同じ行が2つある行列の行列式は 0 である。(性質 2 から簡単。)
5. $i$ 番目の行を定数倍して $j$ 番目から引いても行列式は変わらない。
$$
\left|
\begin{matrix}
a & b  \\
c+ta & d+tb
\end{matrix}
\right|
\overset{3}=
\left|
\begin{matrix}
a & b  \\
c & d
\end{matrix}
\right|
+
\color{gray}{
\underline{
\color{black}{
t\left|
\begin{matrix}
a & b  \\
a & b
\end{matrix}
\right|
} % black
}_{\overset{4}=0}
} % gray
$$
6. 0 だけからなる行があるとき、行列式は 0 である。(性質 3 から簡単。)
7. 上(下)三角行列の行列式は、対角線の積である。
$$
\left|
\begin{matrix}
d_1 & * & *  \\
0 & d_2 & *  \\
0 & 0   & d_3
\end{matrix}
\right|
\overset{5}{=}
\left|
\begin{matrix}
d_1 & 0 & 0  \\
0 & d_2 & 0  \\
0 & 0 & d_3 
\end{matrix}
\right|
\overset{3}{=}
d_1d_2d_3|I|
$$

8. $\mathrm{det}(A) = 0\iff$ A が逆行列を持つ。(性質 7 から簡単。)
9. $|AB| = |A||B|$ である。
  証明 ([1] p264より): $|B|=0$ の場合は性質 8 から簡単。$|B|\neq 0$ とし、比 $D(A)=|AB|/|B|$ を考える。**この比が性質 1-3 を満たすことを確かめる。**すると、$D(A)$ は行列式でなければならないので、$|A|=|AB|/|B|$ を得る。(1): $A=I$ のとき $D(A)=|B|/|B|=1$ である。(2): $A$ のある 2 つの行を交換すると、$AB$ の対応する行も交換される。よって、$D(A)$ の符号が変化する。 (3): $A$ のある行を $t$ 倍すると、$AB$ の対応する行も $t$ 倍される。よって、$D(A)$ も $t$ 倍になる。また、$A$ と $A'$ をある行のみが異なる行列とすると、$AB$、$A'B$ も、対応する行のみが異なる行列である。よって、$|AB+A'B| = |AB| + |A'B|$ が成立して、$D(A+A') = D(A)+D(A')$ となる。
10. $|A^T| = |A|$. (証明: LU分解を考える。$A^t=U^TL^T, A=LU$ と分解される。性質9 と 7 からこれらの行列式はともに $L$ と $U$ の対角線の積であり一致している。

## References

[1] [世界標準MIT教科書 ストラング：線形代数イントロダクション](https://www.amazon.co.jp/dp/B01I4TPXLE)
