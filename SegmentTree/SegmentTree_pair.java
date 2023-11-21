public record P(int fi , int se) { 
    public String toString() { 
        return "("+fi+","+se+")";
    }
}

class SegmentTree_Pair {

    private int n ;
    private P init ;
    P [] tree ;
    private int size ;
    private BinaryOperator<P> op ;

    @SuppressWarnings ("unchecked")
    SegmentTree_Pair(int size , P init , BinaryOperator<P> op) {
        this.size = size;
        this.init = init ;
        this.op = op ;
        n = 1 ;
        while(n <= size) n *= 2 ;
        this.tree = new P[2 * n];
        for(int i = 0 ; i < 2 * n ; i ++ ) tree[i] = init ;
    }

    void update(int index , P value) {
        index += n ;
        tree[index] = value ;
        while(index > 0) {
            index /= 2 ;
            tree[index] = op.apply(tree[2 * index] , tree[2 * index + 1]);
        }
    }

    P query(int l , int r){
        l += n ; r += n ;
        P res = init ;
        while(l < r){
            if(l % 2 == 1){ res = op.apply(res , tree[l]) ; l ++ ; }
            l /= 2 ;
            if(r % 2 == 1){ res = op.apply(res , tree[r - 1]) ; r -- ; }
            r /= 2 ;
        }
        return res ;
    }

    void debug() {
        for(int i = n ; i < n + size ; i ++ ) {
            System.out.print(tree[i]+" ");
        }
        System.out.println();
    }

}