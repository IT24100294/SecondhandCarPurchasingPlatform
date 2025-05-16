package Backend;

// User node and linked list
class UserNode {
    String id, username, email, password, role;
    UserNode next;
    public UserNode(String id, String username, String email, String password, String role) {
        this.id = id; this.username = username; this.email = email;
        this.password = password; this.role = role; this.next = null;
    }
}
class UserLinkedList {
    private UserNode head; private int size = 0;
    public void add(UserNode node) {
        if (head == null) head = node;
        else { UserNode cur = head; while (cur.next != null) cur = cur.next; cur.next = node; }
        size++;
    }
    public boolean remove(String id) {
        if (head == null) return false;
        if (head.id.equals(id)) { head = head.next; size--; return true; }
        UserNode cur = head;
        while (cur.next != null && !cur.next.id.equals(id)) cur = cur.next;
        if (cur.next != null) { cur.next = cur.next.next; size--; return true; }
        return false;
    }
    public UserNode findById(String id) {
        UserNode cur = head;
        while (cur != null) { if (cur.id.equals(id)) return cur; cur = cur.next; }
        return null;
    }
    public boolean update(String id, String username, String email, String password, String role) {
        UserNode user = findById(id);
        if (user != null) {
            user.username = username; user.email = email;
            if (password != null && !password.trim().isEmpty()) user.password = password;
            user.role = role; return true;
        }
        return false;
    }
    public UserNode[] getAll() {
        UserNode[] arr = new UserNode[size]; UserNode cur = head; int i = 0;
        while (cur != null) { arr[i++] = cur; cur = cur.next; }
        return arr;
    }
    public int size() { return size; }
}

// Listing node and linked list
class ListingNode {
    String id, sellerId, make, model, status;
    int year; double price; ListingNode next;
    public ListingNode(String id, String sellerId, String make, String model, int year, double price, String status) {
        this.id = id; this.sellerId = sellerId; this.make = make; this.model = model;
        this.year = year; this.price = price; this.status = status; this.next = null;
    }
}
class ListingLinkedList {
    private ListingNode head; private int size = 0;
    public void add(ListingNode node) {
        if (head == null) head = node;
        else { ListingNode cur = head; while (cur.next != null) cur = cur.next; cur.next = node; }
        size++;
    }
    public boolean remove(String id) {
        if (head == null) return false;
        if (head.id.equals(id)) { head = head.next; size--; return true; }
        ListingNode cur = head;
        while (cur.next != null && !cur.next.id.equals(id)) cur = cur.next;
        if (cur.next != null) { cur.next = cur.next.next; size--; return true; }
        return false;
    }
    public ListingNode findById(String id) {
        ListingNode cur = head;
        while (cur != null) { if (cur.id.equals(id)) return cur; cur = cur.next; }
        return null;
    }
    public boolean update(String id, String sellerId, String make, String model, int year, double price, String status) {
        ListingNode l = findById(id);
        if (l != null) {
            l.sellerId = sellerId; l.make = make; l.model = model;
            l.year = year; l.price = price; l.status = status; return true;
        }
        return false;
    }
    public ListingNode[] getAll() {
        ListingNode[] arr = new ListingNode[size]; ListingNode cur = head; int i = 0;
        while (cur != null) { arr[i++] = cur; cur = cur.next; }
        return arr;
    }
    public int size() { return size; }
    // Simple selection sort by price
    public void selectionSortByPrice() {
        for (ListingNode i = head; i != null; i = i.next) {
            ListingNode min = i;
            for (ListingNode j = i.next; j != null; j = j.next)
                if (j.price < min.price) min = j;
            if (min != i) swap(i, min);
        }
    }
    // Simple selection sort by year
    public void selectionSortByYear() {
        for (ListingNode i = head; i != null; i = i.next) {
            ListingNode min = i;
            for (ListingNode j = i.next; j != null; j = j.next)
                if (j.year < min.year) min = j;
            if (min != i) swap(i, min);
        }
    }
    private void swap(ListingNode a, ListingNode b) {
        String tid = a.id, tsid = a.sellerId, tmake = a.make, tmodel = a.model, tstatus = a.status;
        int tyear = a.year; double tprice = a.price;
        a.id = b.id; a.sellerId = b.sellerId; a.make = b.make; a.model = b.model; a.year = b.year; a.price = b.price; a.status = b.status;
        b.id = tid; b.sellerId = tsid; b.make = tmake; b.model = tmodel; b.year = tyear; b.price = tprice; b.status = tstatus;
    }
}

// Purchase node and linked list
class PurchaseNode {
    String id, buyerId, listingId, date, status;
    PurchaseNode next;
    public PurchaseNode(String id, String buyerId, String listingId, String date, String status) {
        this.id = id; this.buyerId = buyerId; this.listingId = listingId; this.date = date; this.status = status; this.next = null;
    }
}
class PurchaseLinkedList {
    private PurchaseNode head; private int size = 0;
    public void add(PurchaseNode node) {
        if (head == null) head = node;
        else { PurchaseNode cur = head; while (cur.next != null) cur = cur.next; cur.next = node; }
        size++;
    }
    public boolean remove(String id) {
        if (head == null) return false;
        if (head.id.equals(id)) { head = head.next; size--; return true; }
        PurchaseNode cur = head;
        while (cur.next != null && !cur.next.id.equals(id)) cur = cur.next;
        if (cur.next != null) { cur.next = cur.next.next; size--; return true; }
        return false;
    }
    public PurchaseNode findById(String id) {
        PurchaseNode cur = head;
        while (cur != null) { if (cur.id.equals(id)) return cur; cur = cur.next; }
        return null;
    }
    public boolean update(String id, String status) {
        PurchaseNode p = findById(id);
        if (p != null) { p.status = status; return true; }
        return false;
    }
    public PurchaseNode[] getAll() {
        PurchaseNode[] arr = new PurchaseNode[size]; PurchaseNode cur = head; int i = 0;
        while (cur != null) { arr[i++] = cur; cur = cur.next; }
        return arr;
    }
    public int size() { return size; }
}
