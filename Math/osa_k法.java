// O(logN)
class osa_k
{
    private int n ; 
    private int [] min_factor ;
    osa_k(int n)
    {
        this.n = n ;
        this.min_factor = new int[n + 1];
        init();
    }
    void init()
    {
        for(int i = 0 ; i <= n ; i ++ ) min_factor[i] = i ;
        for(int i = 2 ; i * i <= n ; i ++ )
        {
            if(min_factor[i] == i)
            {
                for(int j = 2 ; i * j <= n ; j ++ )
                {
                    if(min_factor[i * j] > i)
                    {
                        min_factor[i * j] = i ;
                    }
                }
            }
        }
    }
    Map<Integer,Integer> factor(int n)
    {
        Map<Integer,Integer> fact = new HashMap<>();
        while(n > 1)
        {
            fact.put(min_factor[n] , fact.getOrDefault(min_factor[n] , 0) + 1);
            n /= min_factor[n];
        }
        return fact ;
    }
}