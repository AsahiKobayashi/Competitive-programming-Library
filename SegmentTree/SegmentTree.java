class SegmentTree{
        
        private int n ;
        private long init ;
        private long [] tree ;
        private int size ;
        private BinaryOperator<Long> op ;

        SegmentTree(int size , long init , BinaryOperator<Long> op){
            this.size = size;
            this.init = init ;
            this.op = op ;
            n = 1 ;
            while(n <= size) n *= 2 ;
            this.tree = new long[2 * n];
            for(int i = 0 ; i < 2 * n ; i ++ ) tree[i] = init ;
        }
        void update(int index , long value){
            index += n ;
            tree[index] = value ;
            while(index > 0){
                index /= 2 ;
                tree[index] = op.apply(tree[2 * index] , tree[2 * index + 1]);
            }
        }
        long query(int l , int r){
            l += n ; r += n ;
            long res = init ;
            while(l < r){
                if(l % 2 == 1){ res = op.apply(res , tree[l]) ; l ++ ; }
                l /= 2 ;
                if(r % 2 == 1){ res = op.apply(res , tree[r - 1]) ; r -- ; }
                r /= 2 ;
            }
            return res ;
        }
        void debug() 
        {
            for(int i = n ; i < n + size ; i ++ ) {
                out.print(tree[i]+" ");
            }
            out.println();
            out.flush();
        }
    }