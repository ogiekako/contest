# 多項式補間 (Interpolation)

$n+1$ 個の条件
$$
f(x_i) = y_i\  (i = 0,\cdots, n)
$$
を満たす、$n$ 次の多項式
$$
f(x) = a_0 + a_1x + \cdots + a_nx^n
$$
を $O(n^2)$ 時間で求める。

## ラグランジュ補間

$n$ = 2 の場合で説明する。

$$
f(x) = y_1\frac{(x-x_3)(x-x_3)}{(x_1-x_2)(x_1-x_3)} + y_2\frac{(x-x_1)(x-x_3)}{(x_2-x_1)(x_2-x_3)} + y_3\frac{(x-x_1)(x-x_2)}{(x_3-x_1)(x_3-x_2)}
$$

が求める多項式になっている。
分母を計算するのは $O(n^2)$ でできる。分子に関しては、$(x-x_1)(x-x_2)(x-x_3)$ を計算してから、各 $i$ に関して $(x-x_i)$ で割るようにすれば全体で $O(n^2)$ 時間で求められる。

## ニュートン補間

$n$ = 2 の場合で説明する。 $ f(0) = 0, f(1) = 1, f(2) = 4 $ となる 2次多項式 $f$ を求める。

$f_{ij}$ を、$i \ldots j$ について条件をみたす 1 次式とする。

$$
\begin{align*}
f_{01}(x) = 0 + \frac{1-0}{1-0}(x-0) = 0 + \color{blue}1(x-0) \\
f_{12}(x) = 1 + \frac{4-1}{2-1}(x-1) = 1 + \color{red}3(x-1)
\end{align*}
$$

とかける。ここから $f_{02}$ を求めたい。
$$
f_{02}(x) = f_{01}(x) + \frac{x-0}{2-0}(f_{12}(x) - f_{01}(x))
$$
が条件をみたすことがわかる。右辺の $x^2$ の係数は、
$$
\frac{\color{red}3 - \color{blue}1}{2 - 0}
$$
となっている。
よって、
$$
f_{02}(x) = f_{01}(x) + \frac{3-1}{2-0}(x-0)(x-1)
$$
となることがわかった。

このような感じで、
$$
f_{ij} = a_{i,i} + a_{i,i+1}(x-x_i) + \cdots a_{i,j}(x-x_i)\cdots(x-x_{j-1})
$$
となるような係数のテーブル $a$ を
$$
\begin{align*}
a_{i,i}  &= y_i \\
a_{i,j}  &= (a_{i+1,j} - a_{i,j-1})/(x_j-x-i)
\end{align*}
$$
と計算し、$f$ を求める。

### ソースコード
https://github.com/ogiekako/contest/search?utf8=%E2%9C%93&q=interpolation+language%3AJava+path%3Amain%2Fnet%2Fogiekako&type=Code
