class SegmentTree <T> {

    private int n ;
    private T e ;
    private T [] dat ;
    private int size ;
    private BinaryOperator<T> op ;

    @SuppressWarnings ("unchecked")
    SegmentTree(int size , T e , BinaryOperator<T> op) {
        this.size = size;
        this.e = e ;
        this.op = op ;
        n = 1 ;
        while(n <= size) n *= 2 ;
        this.dat = (T []) new Object[2 * n];
        for(int i = 0 ; i < 2 * n ; i ++ ) dat[i] = e ;
    }

    public void update(int index , T value) {
        index += n ;
        dat[index] = value ;
        while(index > 0) {
            index /= 2 ;
            dat[index] = op.apply(dat[2 * index] , dat[2 * index + 1]);
        }
    }

    public T query(int l , int r){
        l += n ; r += n ;
        T res = e ;
        while(l < r){
            if(l % 2 == 1){ res = op.apply(res , dat[l]) ; l ++ ; }
            l /= 2 ;
            if(r % 2 == 1){ res = op.apply(res , dat[r - 1]) ; r -- ; }
            r /= 2 ;
        }
        return res ;
    }
    
}
