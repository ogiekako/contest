# 算数

$S\subseteq T$  `(S|T) == T`

$\lfloor n/k \rfloor$  `n/k`
$\lceil n/k \rceil$  `(n+k-1)/k`
$\lfloor \log n\rfloor$ `n == 0 ? 0 : 63 - Long.numberOfLeadingZeros(n)`
$\lceil \log n\rceil$ `n == 0 ? 0 : 64 - Long.numberOfLeadingZeros(n-1)`

$n$ を $k$ 個になるべく均等に分割する: $\lceil n/k \rceil$ が $n\%k$ 個、$\lfloor n/k \rfloor$ が $n - n\%k$ 個。
