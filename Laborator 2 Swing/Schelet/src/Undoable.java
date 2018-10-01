import javax.swing.*;

public interface Undoable {
    public void undo();
    public void redo();
}

class addCommand implements Undoable {

    private DefaultListModel _model;
    private String           _addedElem;

    addCommand(DefaultListModel model, String elem){
        _model = model;
        _addedElem = elem;
    }

    @Override
    public void undo() {

        _model.removeElement(_addedElem);
    }

    @Override
    public void redo() {
        _model.addElement(_addedElem);
    }
}

class removeCommand implements Undoable{

    private DefaultListModel _model;
    private String           _deletedElem;
    private int          _index;


    removeCommand(DefaultListModel model, String elem, int index){
        _model = model;
        _deletedElem = elem;
        _index = index;
    }

    @Override
    public void undo() {
        _model.addElement(_deletedElem);
    }

    @Override
    public void redo() {
        if (_model.contains(_deletedElem)) {
            _model.addElement(_deletedElem);
        }
    }

}
