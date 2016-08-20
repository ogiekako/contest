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

## ラグランジュの補間公式

$n$ = 2 の場合で説明する。

$$
f(x) = y_1\frac{(x-x_3)(x-x_3)}{(x_1-x_2)(x_1-x_3)} + y_2\frac{(x-x_1)(x-x_3)}{(x_2-x_1)(x_2-x_3)} + y_3\frac{(x-x_1)(x-x_2)}{(x_3-x_1)(x_3-x_2)}
$$

が求める多項式になっている。
分母を計算するのは $O(n^2)$ でできる。分子に関しては、$(x-x_1)(x-x_2)(x-x_3)$ を計算してから、各 $i$ に関して $(x-x_i)$ で割るようにすれば全体で $O(n^2)$ 時間で求められる。

### ソースコード
https://github.com/ogiekako/contest/search?utf8=%E2%9C%93&q=interpolation+language%3AJava+path%3Amain%2Fnet%2Fogiekako&type=Code
