import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.*;

public class Main extends JPanel {

	private DefaultListModel	model;										// list model
	private ReverseListModel	reverseListModel;
	private UndoManager			undoManager = new UndoManager();
	private JList				list, mirror;								// lists
	private JTextField			tName			= new JTextField(10);		// name field
	private JButton				bAdd			= new JButton("Add");		// add button
	private JButton				bRemove			= new JButton("Remove");	// remove button
	private JButton				undoButton		= new JButton("Undo");
	private JButton				redoButton		= new JButton("Redo");
	private String				removedElement  = new String();

	public Main() {
		init();
	}

	public void init() {
		// initialize model
		model = new DefaultListModel();
		reverseListModel = new ReverseListModel(model);

		// TODO 1: populate model

		// initialize lists, based on the same model
		list	= new JList(model);
		mirror	= new JList(reverseListModel);


		list.setBackground(Color.cyan);
		mirror.setBackground(Color.RED);
		// TODO 6: redefine mirror so as to use a ReverseListModel instance on top of 'model'

		// main panel: top panel, bottom panel
		JPanel top = new JPanel(new GridLayout(1, 0)); // 1 row, any number of columns
		JPanel bottom = new JPanel(new FlowLayout());
		this.setLayout(new BorderLayout());
		this.add(top, BorderLayout.CENTER);
		this.add(bottom, BorderLayout.SOUTH);

		// top panel: the two lists (scrollable)
		top.add(new JScrollPane(list));
		top.add(new JScrollPane(mirror));

		// bottom panel: name field, add button, remove button
		bottom.add(tName);
		bottom.add(bAdd);
		bottom.add(bRemove);
		bottom.add(undoButton);
		bottom.add(redoButton);

//		model.addListDataListener(new ListDataListener() {
//			@Override
//			public void intervalAdded(ListDataEvent e) {
//
//			}
//
//			@Override
//			public void intervalRemoved(ListDataEvent e) {
//				reverseListModel.removeElement(removedElement);
//			}
//
//			@Override
//			public void contentsChanged(ListDataEvent e) {
//
//			}
//		});
		bAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 2: call the method for obtaining the text field's content
				String text = tName.getText();

				if (text.isEmpty()) {
					JOptionPane.showMessageDialog(
							null, "Name is empty!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				else
				{
					if (!model.contains(text)) {

						model.addElement(text);
						Undoable cmd = new addCommand(model, text);
						undoManager.addAction(cmd);

//					model.addElement(text);
					}
					tName.setText("");
				}

				// TODO 3: add new element to model
			}
		});

		// TODO 4: add listener for Remove button
		bRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!model.isEmpty()) {
					if (list.isSelectionEmpty())
					{
						JOptionPane.showMessageDialog(
								null, "No selected element!", "Error", JOptionPane.ERROR_MESSAGE);
//						model.removeElement(model.lastElement());
					}
					else {
						int index = model.indexOf(removedElement);
						removedElement = list.getSelectedValue().toString();
						model.removeElement(removedElement);
						undoManager.addAction(new removeCommand(model, removedElement, index));
					}
				}
			}
		});

		undoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!model.isEmpty()){
					undoManager.undo();
				}
			}
		});

		redoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!model.isEmpty()){
					undoManager.redo();
				}
			}
		});


	}

	public static void buildGUI() {
		JFrame frame = new JFrame("Swing stuff"); // title
		frame.setContentPane(new Main()); // content: the JPanel above
		frame.setSize(500, 300); // width / height
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit application when window is closed
		frame.setVisible(true); // show it!
		frame.setLocationRelativeTo(null);
	}


	public static void main(String[] args) {
		// run on EDT (event-dispatching thread), not on main thread!
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				buildGUI();
			}
		});
	}

}
