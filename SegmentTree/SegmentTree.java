class SegmentTree <T> {

    private int n ;
    private T init ;
    private T [] tree ;
    private int size ;
    private BinaryOperator<T> op ;

    @SuppressWarnings ("unchecked")
    SegmentTree(int size , T init , BinaryOperator<T> op) {
        this.size = size;
        this.init = init ;
        this.op = op ;
        n = 1 ;
        while(n <= size) n *= 2 ;
        this.tree = (T []) new Object[2 * n];
        for(int i = 0 ; i < 2 * n ; i ++ ) tree[i] = init ;
    }

    public void update(int index , T value) {
        index += n ;
        tree[index] = value ;
        while(index > 0) {
            index /= 2 ;
            tree[index] = op.apply(tree[2 * index] , tree[2 * index + 1]);
        }
    }

    public T query(int l , int r){
        l += n ; r += n ;
        T res = init ;
        while(l < r){
            if(l % 2 == 1){ res = op.apply(res , tree[l]) ; l ++ ; }
            l /= 2 ;
            if(r % 2 == 1){ res = op.apply(res , tree[r - 1]) ; r -- ; }
            r /= 2 ;
        }
        return res ;
    }
        
}
