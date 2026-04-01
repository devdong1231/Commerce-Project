public class MenuItem implements MenuCommand {
    private String name;
    private Runnable action;

    MenuItem(String name, Runnable action) {
        this.name = name;
        this.action = action;
    }

    public String getMenuName() {
        return name;
    }

    @Override
    public void execute() {
        action.run();
    }
}
