class RBST {

	static final int INF = 1001001001;
	static Random random = new Random();
	
	static final int POOL_SIZE = 200000 + 100;
	static int sp = 0;
	static Node [] pool;
	
	static class Node {
	    Node l, r;
	    int val;
	    int size; // subtree size
	    int min;
	}
	
	// 初期化
	// RBST.Node tree = null 
	// RBST.Node rev_tree = null
	
	static class TempNode {
	    Node n1 , n2 ;
	    TempNode(Node n1 , Node n2) {
		this.n1 = n1 ;
		this.n2 = n2 ;
	    }
	}
	
	static {
		pool = new Node[POOL_SIZE];
		for (int i = 0; i < pool.length; i++) pool[i] = new Node();
	}
	
	static Node newNode(int val) {
		Node t = pool[sp++];
		t.l = t.r = null;
		t.size = 1;
		t.val = val;
		t.min = val;
		return t;
	}
	
	private static final Node[] EP = new Node[2];
	
	public static int size(Node t) { 
	    return t == null ? 0 : t.size; 
	}
	
		public static int min(Node t) { 
	    return t == null ? INF : t.min; 
	}
	
	public static TempNode reverse(Node t1 , Node t2 , int a , int b) {
	    int N = t2.size ;
	    Node[] s = split(t1 , a);
	    Node[] s1 = split(s[1] , b - a);
	    Node[] q = split(t2 , N - b);
	    Node[] q1 = split(q[1] , b - a);
	    return new TempNode(merge(s[0] , merge(q1[0] , s1[1])) , merge(q[0] , merge(s1[0] , q1[1])));
	}
	
	public static int get(Node t , int k) {
	    int s = size(t.l);
	    if(s > k) return get(t.l , k);
	    else if(s < k) return get(t.r , k - s - 1);
	    else return t.val ;
	}
	
	public static void showTree(Node t) {
	    StringBuilder data = new StringBuilder();
	    for(int i = 0 ; i < t.size ; i ++) {
		data.append(get(t , i));
		if(i != t.size - 1) data.append(" , ");
	    }
	    System.out.println(data.toString());
	}
	
	public static int min(Node t, int a, int b) {
		if (t == null) return INF;
		if (a <= 0 && size(t) <= b) {
			return t.min;
		}
		int res = INF;
		if (a < size(t.l)) {
			int x = min(t.l, a, b);
			if (res > x) res = x;
		}
		if (a <= size(t.l) && size(t.l) < b) {
			if (res > t.val) res = t.val;
		}
		if (b >= size(t.l) + 1) {
			int x = min(t.r, a - size(t.l) - 1, b - size(t.l) - 1);
			if (res > x) res = x;
		}
		return res;
	}
	
	public static Node insert(Node t, int k, int val) {
		Node[] s = split(t, k);
		return merge(merge(s[0], newNode(val)), s[1]);
	}
	
	// [0, n) -> [0, k) + [k+1, n)
	public static Node erase(Node t, int k) {
		Node[] s = split(t, k);
		Node[] s2 = split(s[1], 1);
		return merge(s[0], s2[1]);
	}
	
	public static void update(Node t, int k, int val) {
		if (t == null) return;
		if (size(t.l) > k)
			update(t.l, k, val);
		else if (size(t.l) == k)
			t.val = val;
		else
			update(t.r, k - size(t.l) - 1, val);
		update(t);
	}
	
	public static Node rotationLeft(Node t, int a, int b) {
		Node[] r1 = split(t, a);
		Node[] r2 = split(r1[1], b - a - 1);
		Node[] r3 = split(r2[1], 1);
		return merge(merge(r1[0], r3[0]), merge(r2[0], r3[1]));
	}
	
	public static Node rotationRight(Node t, int a, int b) {
		Node[] r1 = split(t, a);
		Node[] r2 = split(r1[1], 1);
		Node[] r3 = split(r2[1], b - a - 1);
		return merge(merge(r1[0], r3[0]), merge(r2[0], r3[1]));
	}
	
	private static Node update(Node t) {
		t.size = size(t.l) + size(t.r) + 1;
		t.min = Math.min(Math.min(min(t.l), min(t.r)), t.val);
		return t;
	}
	
	private static Node[] split(Node t, int k) { // [0, k), [k, n)
		if (t == null) return EP;
		if (k <= size(t.l)) {
			Node[] s = split(t.l, k);
			t.l = s[1];
			return new Node[] {s[0], update(t)};
		} else {
			Node[] s = split(t.r, k - size(t.l) - 1);
			t.r = s[0];
			return new Node[] {update(t), s[1]};
		}
	}
	
	private static Node merge(Node t1, Node t2) {
		if (t1 == null) return t2;
		if (t2 == null) return t1;
		if (random.nextInt(t1.size + t2.size) < t1.size) {
			t1.r = merge(t1.r, t2);
			return update(t1);
		} else {
			t2.l = merge(t1, t2.l);
			return update(t2);
		}
	}

}
