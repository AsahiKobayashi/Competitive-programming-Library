boolean [] Eratosthenes(){
        int limit = 10101010;
        boolean [] isprime = new boolean[limit + 1];
        Arrays.fill(isprime,true);
        isprime[0] = false;
        isprime[1] = false;
        for(int i = 2 ; i <= limit ; i ++) {
            if(!isprime[i]) continue;
            for(int j = i * 2 ; j <= limit ; j += i){
                isprime[j] = false;
            }
        }
        return isprime;
}
