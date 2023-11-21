class Pollard {
        
        private final BigInteger ZERO = new BigInteger("0");
        private final BigInteger ONE  = new BigInteger("1");
        private final BigInteger TWO  = new BigInteger("2");
        private final java.security.SecureRandom random = new java.security.SecureRandom();

        private BigInteger f (BigInteger x, BigInteger c, BigInteger N) {
            return x.multiply(x).mod(N).add(c);
        }
        
        private BigInteger[] smallPrimes = {
                new BigInteger("2")
        };
        
        private BigInteger smallPrimeDivisor(BigInteger N) {
            for(BigInteger p : smallPrimes) {
                if (N.mod(p) == ZERO)
                    return p;
            }
            return ZERO;
        }
        
        public BigInteger divisor(BigInteger N) {
            BigInteger c = new BigInteger(N.bitLength(), random);
            BigInteger x = new BigInteger(N.bitLength(), random);
            BigInteger y = x;
            BigInteger divisor = ONE;
            if (N.mod(TWO).compareTo(ZERO) == 0) return TWO;
            while((divisor.compareTo(ONE)) == 0) {
                x = f(x,c,N);
                y = f(f(y,c,N),c,N);
                divisor = x.subtract(y).gcd(N);
            }
            return divisor;
        }
        
        public Map<Long, Integer> fact (long n) {
            Map<Long, Integer> map = new HashMap<>();
            factor(new BigInteger(String.valueOf(n)), map);
            return map;
        }
        
        private void factor(BigInteger N, Map<Long, Integer> map) {
            if (N.compareTo(ONE) == 0) return;
            if (N.isProbablePrime(10)) {
                long n = Long.parseLong(N.toString());
                if (map.get(n) == null) map.put(n, 1);
                else map.put(n, map.get(n) + 1);
                return; }
            if (N.bitLength() > 74) {
                return;
            }
            BigInteger p = smallPrimeDivisor(N);
            BigInteger d = (p != ZERO) ? p : divisor(N);
            factor(d, map);
            factor(N.divide(d), map);
        }
        
}
