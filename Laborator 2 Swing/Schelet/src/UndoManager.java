import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class UndoManager {
    List<Undoable> stepsList;
    ListIterator<Undoable> iterator;

    public UndoManager() {
        stepsList = new LinkedList<>();
        iterator = stepsList.listIterator();
    }

    public void undo() {
        if (iterator.hasPrevious()) {
            iterator.previous().undo();
        }
    }

    public void redo() {
        if (iterator.hasNext()) {
            iterator.next().redo();
        }
    }

    public void addAction(Undoable action) {
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
        iterator.add(action);

    }
}
