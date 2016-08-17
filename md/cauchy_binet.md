# コーシー・ビネの公式

http://mathtrain.jp/binetcauchy

$A$ を $m\times n$ 行列、$B$ を $n\times m$ 行列とするとき、以下が成立する。

$$ |AB| = \sum_{S} |A^S||B_S| $$

ただし、$S$ は、$\{1, ..., n\}$ の大きさ $m$ の部分集合をすべて動くものとし、$A^S$ は、$A$ の対応する列をまとめたもの、$B_S$ は $B$ の対応すする行をまとめたものとする。

例:$$
\left|
\left(
\begin{matrix}
1 & 2 & 3 \\
4 & 5 & 6
\end{matrix}
\right)
\left(
\begin{matrix}
1 & 4 \\
2 & 5 \\
3 & 6 
\end{matrix}
\right)
\right|
=
\left|
\begin{matrix}
1 & 2 &  \\
4 & 5 & 
\end{matrix}
\right|
\left|
\begin{matrix}
1 & 4 \\
2 & 5 \\
 &  
\end{matrix}
\right|
+
\left|
\begin{matrix}
1 &  & 3 \\
4 &  & 6
\end{matrix}
\right|
\left|
\begin{matrix}
1 & 4 \\
 &  \\
3 & 6
\end{matrix}
\right|
+
\left|
\begin{matrix}
 & 2 & 3  \\
 & 5 & 6
\end{matrix}
\right|
\left|
\begin{matrix}
 &  \\
2 & 5 \\
3 & 6
\end{matrix}
\right|
$$

## 証明

以下の様な $(n+m)\times(n+m)$ ブロック行列を考える。
$$
\left(
\begin{array}{ccc:c}
&& & \\
&I&& B \\
&& & \\
\hdashline
&A&&O \\
\end{array}
\right)
$$

掃き出し法のノリで、$A$ を掃き出すことにより、
$$
\left|
\begin{array}{ccc:c}
&& & \\
&I&& B \\
&& & \\
\hdashline
&A&&O \\
\end{array}
\right|
=
\left|
\begin{array}{ccc:c}
&& & \\
&I&& B \\
&& & \\
\hdashline
&O&&-AB \\
\end{array}
\right|
=
|-AB|=(-1)^m|AB|
$$

を得る。
また、置換の和という見かたでこの行列式を考えると、和に寄与するのは、$I$ から、$n-m$ 個とり、$A$, $B$ からそれぞれ $I$ で選ばれなかった列、行で $m$ 個とった場合のみである。
$I$ で取られなかったインデックス $S$ を固定すると、$A$ でのとりかたは $B$ と独立なので、寄与の和は符号を無視すれば $|A^S||B_S|$ となる。符号については、置換をもとに戻すためには、$A$ 側と $B$ 側の置換を整えるのに加えて、$A$ と $B$ の対応する $m$ 個の行(列)を交換する必要があるので、$(-1)^m$ 倍となる。よって、$S$ に対する寄与は、$(-1)^m|A^S||B_S|$ である。これをすべての $S$ について足し合わせ、先に得た和と比較することで所望の公式を得る。

$$
\left(
\begin{array}{ccc:cc}
1&& & \color{green}{*}& \\
&\color{red}{1}& &  & \\
&&1 &  &\color{blue}{*} \\
\hdashline
\color{green}{*}&&  &  & \\
&& \color{blue}{*} && \\
\end{array}
\right)
$$


























