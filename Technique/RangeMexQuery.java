    /*
     * 　機能 : 範囲 Mexクエリ 1クエリにつきO(logN) . (変更なし,オフラインに限る)
     */

    class RangeMexQuery {

        private int INF = (int) 1e9;
        private int MAXN = (int) 2e6 + 5;
        private int MAXM = (int) 2e6 + 5;
        private Query[] qry ;
        private int[] vis ;
        private int[] init_mex ;
        private int[] next_idx ;
        private IntervalTree root;
        private int n, m;
        private int[] a ;

        public RangeMexQuery(int n , int m) {
            this.n = n ;
            this.m = m ;
            this.qry = new Query[MAXM];
            this.vis = new int[MAXN];
            this.init_mex = new int[MAXN];
            this.next_idx = new int[MAXN];
            this.a = new int[MAXN];
        }
        
        private class Query {
            int ql, qr, id, mex;

            void set(int ql , int qr , int _id) {
                this.ql = ql ;
                this.qr = qr ;
                this.id = _id;
            }
        }

        //[l , r]
        public void setQuery(int l ,int r ,int id) {
            qry[id] = new Query();
            qry[id].set(l , r , id);
        }

        public void setValue(int id , int value) {
            a[id] = value ; 
            if (a[id] >= MAXN) a[id] = MAXN - 1;
        }

        private class IntervalTree {

            private int lb, rb, size, min_tag;

            private IntervalTree lc, rc;

            IntervalTree() {
                min_tag = INF;
            }

            void push() {
                if (lb == rb) return;
                if (min_tag == INF) return;
                if (lc != null) lc.min_tag = Math.min(lc.min_tag, min_tag);
                if (rc != null) rc.min_tag = Math.min(rc.min_tag, min_tag);
                min_tag = INF;
            }
        }

        private void init() {
            Arrays.fill(vis, -1);
            for (int i = n - 1; i >= 0; --i) {
                next_idx[i] = vis[a[i]];
                vis[a[i]] = i;
            }
            Arrays.fill(vis, 0);
            int cur_mex = 0;
            for (int i = 0; i < n; ++i) {
                if (a[i] < MAXN) vis[a[i]] = 1;
                while (vis[cur_mex] != 0) ++cur_mex;
                init_mex[i] = cur_mex;
            }
            root = new IntervalTree();
            build(root, 0, n - 1);
        }

        private void build(IntervalTree t, int lb, int rb) {
            t.lb = lb;
            t.rb = rb;
            t.size = rb - lb + 1;
            if (lb == rb) {
                t.min_tag = init_mex[lb];
                return;
            }
            int mid = (lb + rb) / 2;
            t.lc = new IntervalTree();
            t.rc = new IntervalTree();
            build(t.lc, lb, mid);
            build(t.rc, mid + 1, rb);
        }

        private void update(IntervalTree t, int ql, int qr, int v) {
            if (qr < t.lb || t.rb < ql) return;
            if (ql <= t.lb && t.rb <= qr) {
                t.min_tag = Math.min(t.min_tag, v);
                return;
            }
            t.push();
            update(t.lc, ql, qr, v);
            update(t.rc, ql, qr, v);
        }

        private int query(IntervalTree t, int k) {
            if (t.lb == t.rb) return t.min_tag;
            t.push();
            int mid = (t.lb + t.rb) / 2;
            if (k <= mid) return query(t.lc, k);
            else return query(t.rc, k);
        }

        public void run() {
            init();
            Arrays.sort(qry, 0, m, (q1, q2) -> Integer.compare(q1.ql, q2.ql));
            int l = 0;
            for (int i = 0; i < m; ++i) {
                while (l < qry[i].ql) {
                    int nxt = next_idx[l];
                    if (nxt < 0) nxt = MAXN;
                    if (l + 1 <= nxt - 1) update(root, l + 1, nxt - 1, a[l]);
                    ++l;
                }
                qry[i].mex = query(root, qry[i].qr);
            }
            Arrays.sort(qry, 0, m, (q1, q2) -> Integer.compare(q1.id, q2.id));
        }
    }
