import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class ReverseListModel<T> extends AbstractListModel<T> {

    private static final long serialVersionUID = 1L;
    private DefaultListModel<T> model;

    public ReverseListModel(DefaultListModel model) {
        super();

        ReverseListModel	revModel;
        ListDataListener[] listeners;
        listeners = model.getListDataListeners();
        for (int i = 0; i < listeners.length; i++) {
            this.addListDataListener(listeners[i]);
        }
        revModel = this;
        model.addListDataListener(new ListDataListener() {
            @Override
            public void contentsChanged(ListDataEvent arg0) {
                revModel.fireContentsChanged(this, 0, revModel.getSize());
                // TODO Auto-generated method stub
            }

            @Override
            public void intervalAdded(ListDataEvent arg0) {
                revModel.fireIntervalAdded(this, 0, revModel.getSize());
                // TODO Auto-generated method stub
            }

            @Override
            public void intervalRemoved(ListDataEvent arg0) {
                revModel.fireIntervalRemoved(this, 0, revModel.getSize());
                // TODO Auto-generated method stub

            }

        });

        this.model = model;
    }

    @Override
    public T getElementAt(int arg0) {
        // TODO Auto-generated method stub
        if (model == null)
            return null;
        if (model.size() > arg0 && arg0 >= 0) {
            return model.getElementAt(model.size() - arg0 - 1);
        }
        return null;
    }

    @Override
    public int getSize() {
        if (model != null)
            return model.size();
        // TODO Auto-generated method stub
        return 0;
    }

}

