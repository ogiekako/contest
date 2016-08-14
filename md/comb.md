# 組合せの基礎

## 区別できないものの分割

[Stars and bars](https://en.wikipedia.org/wiki/Stars_and_bars_%28combinatorics%29)

和が $n$ になるような $k$ 要素のタプルは何通りあるかという問題を考える。

- 要素は正とする場合
  - ●|●●|●●|● という感じになる。$n-1$ 個のすき間に $k-1$ 個の仕切りを入れる方法の数なので、$\binom{n-1}{k-1}$ 通り。
- 要素は非負とする場合
  - |●●||●●●●  などとなっていても良い。$k-1$ 個の仕切りと、$n$ 個の● を並べる方法の数なので、$\binom{n+k-1}{k-1}$ 通り。

$n$ 種類のものから重複を許して $k$ 個選ぶ方法の数は、重複組合せと呼ばれ、$\left(\binom nk\right)$ で表されるが、これは、非負で、和が $k$ になるような $n$ 要素のタプルが何通りあるかと同じなので、$\binom{n+k-1}{n-1}$ である。(例: $n=4, k=3$   `{2, 2, 4} ↔ (0, 2, 0, 1)`)

## References

- [Concrete Mathematics](https://notendur.hi.is/pgg/%28ebook-pdf%29%20-%20Mathematics%20-%20Concrete%20Mathematics.pdf)

