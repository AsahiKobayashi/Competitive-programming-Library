class SegmentTree<T> {

    private T[] tree;
    private int n;
    private Monoid<T> monoid;

    @SuppressWarnings("unchecked")
    public SegmentTree(int n , Monoid<T> monoid) {
        this.n = n;
        this.monoid = monoid;
        this.tree = (T[]) new Object[2 * n];
        Arrays.fill(tree, monoid.e());
    }

    @SuppressWarnings("unchecked")
    public SegmentTree(T [] arr, Monoid<T> monoid) {
        this.n = arr.length;
        this.monoid = monoid;
        this.tree = (T[]) new Object[2 * n];
        build(arr);
    }

    private void build(T [] arr) {
        for (int i = 0; i < n; i++) {
            tree[n + i] = arr[i];
        }
        for (int i = n - 1; i > 0; i--) {
            tree[i] = monoid.op(tree[2 * i], tree[2 * i + 1]);
        }
    }

    public void update(int index, T value) {
        index += n;
        tree[index] = value;
        while (index > 1) {
            index /= 2;
            tree[index] = monoid.op(tree[2 * index], tree[2 * index + 1]);
        }
    }

    public T query(int index) {
        return query(index,index+1);
    }

    public T query(int l, int r) {
        T resLeft = monoid.e();
        T resRight = monoid.e();
        l += n;
        r += n;
        while (l < r) {
            if ((l & 1) == 1) {
                resLeft = monoid.op(resLeft, tree[l]);
                l++;
            }
            if ((r & 1) == 1) {
                r--;
                resRight = monoid.op(tree[r], resRight);
            }
            l /= 2;
            r /= 2;
        }
        return monoid.op(resLeft, resRight);
    }

}