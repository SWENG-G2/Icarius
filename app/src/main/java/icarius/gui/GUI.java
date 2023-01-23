package icarius.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.*;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;


public class GUI {
	private static Table table;
	private static Text idText;
	private static Text campusText;
	private static Text duckNameText;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		
		Display display = Display.getDefault();
		Shell shell = new Shell();
		shell.setSize(650, 400);
		shell.setText("Database GUI");
		
		
		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		table.setBounds(298, 32, 324, 296);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		String[] titles = {"ID", "Campus", "Duck name"};
		for (String title : titles) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(title);
		}
		
		
		for (int i=0; i<titles.length; i++) {
			table.getColumn(i).pack();
			table.getColumn(i).setWidth(75);
		}
		
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(40, 101, 20, 15);
		lblNewLabel.setText("ID");
		
		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setBounds(40, 151, 55, 15);
		lblNewLabel_1.setText("Campus");
		
		Label lblNewLabel_2 = new Label(shell, SWT.NONE);
		lblNewLabel_2.setBounds(40, 201, 65, 15);
		lblNewLabel_2.setText("Duck name");
		
		Label lblNewLabel_3 = new Label(shell, SWT.NONE);
		lblNewLabel_3.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.BOLD));
		lblNewLabel_3.setBounds(78, 40, 134, 25);
		lblNewLabel_3.setText("Duck Database");
		
		idText = new Text(shell, SWT.BORDER | SWT.CENTER);
		idText.setBounds(129, 98, 119, 21);
		
		campusText = new Text(shell, SWT.BORDER | SWT.CENTER);
		campusText.setBounds(129, 148, 119, 21);
		
		duckNameText = new Text(shell, SWT.BORDER | SWT.CENTER);
		duckNameText.setBounds(129, 198, 119, 21);
		
		Label label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(31, 93, 223, 2);
		
		Label label_3 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_3.setBounds(31, 222, 223, 2);
		
		Label label_4 = new Label(shell, SWT.SEPARATOR);
		label_4.setBounds(254, 93, 2, 128);
		
		Label label_4_1 = new Label(shell, SWT.SEPARATOR);
		label_4_1.setBounds(31, 93, 2, 129);
		
		Button btnCreateDuck = new Button(shell, SWT.FLAT);
		btnCreateDuck.setSelection(true);
		btnCreateDuck.setBounds(105, 230, 75, 25);
		btnCreateDuck.setText("Create Duck");
		btnCreateDuck.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if ((campusText.getText() == null || campusText.getText().trim().isEmpty() != true)
					 && (duckNameText.getText() == null || duckNameText.getText().trim().isEmpty() != true)) {
					final TableItem item = new TableItem(table, SWT.NONE);
					int idCount = table.getItemCount()-1;
					item.setText(0, String.valueOf(idCount));
				    item.setText(1, campusText.getText());
				    item.setText(2, duckNameText.getText());
				}
			}
		});
		
		Button btnUpdateDuck = new Button(shell, SWT.FLAT);
		btnUpdateDuck.setText("Update Duck");
		btnUpdateDuck.setSelection(true);
		btnUpdateDuck.setBounds(105, 261, 75, 25);
		
		Button btnDeleteDuck = new Button(shell, SWT.FLAT);
		btnDeleteDuck.setText("Delete Duck");
		btnDeleteDuck.setSelection(true);
		btnDeleteDuck.setBounds(105, 292, 75, 25);
		btnDeleteDuck.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent event) {
				try {
					table.remove(Integer.valueOf(idText.getText()));
				}
				catch(Exception e) {
					System.out.println("Doesn't exist");
				}
			}
		});
		Menu menu = new Menu(shell, SWT.POP_UP);
		table.setMenu(menu);
		MenuItem item = new MenuItem(menu, SWT.PUSH);
		item.setText("Delete");
		item.addListener(SWT.Selection, event -> table.remove(table.getSelectionIndices()));
		
		
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
