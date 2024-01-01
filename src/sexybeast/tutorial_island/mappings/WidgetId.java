package sexybeast.tutorial_island.mappings;

public enum WidgetId {
    BRONZE_DAGGER(312, 9, 2),
    CHARACTER_CREATION_ARROWS(269,-1,-1);


    private int root, child, grandChild;

    WidgetId(int root, int child, int grandChild) {
        this.root = root;
        this.child = child;
        this.grandChild = grandChild;
    }


    public int getRoot() {
        return root;
    }

    public int getChild() {
        return child;
    }

    public int getGrandChild() {
        return grandChild;
    }
}
