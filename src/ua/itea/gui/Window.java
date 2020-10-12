package ua.itea.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import ua.itea.db.EmptyTableException;
import ua.itea.model.InvalidArgumentsException;
import ua.itea.model.ShapeFactory;
import ua.itea.service.ShapeStorageService;

public class Window extends JFrame {
	private ShapeFactory shapeFactory;
	private ShapeStorageService shapeStorageService;
	
	private JPanel currentJPanel;
	
	private JPanel actionSelectorPanel;
	private JPanel shapeSelectorPanel;
	
	private JPanel circleSetterPanel;
	private JPanel rectangleSetterPanel;
	private JPanel triangleComputationMethodPanel;
	private JPanel firstMethodComputationPanel;
	private JPanel secondMethodComputationPanel;
	
	
	public Window(ShapeFactory shapeFactory, 
				  ShapeStorageService shapeStorageService) {
		
		this.shapeFactory = shapeFactory;
		this.shapeStorageService = shapeStorageService;
		
		actionSelectorPanel = new ActionSelectorPanel();
		setPanel(actionSelectorPanel);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void setPanel(JPanel panel) {
		if (currentJPanel != null) {
			currentJPanel.setVisible(false);	
		}
		
		currentJPanel = panel;
		currentJPanel.setVisible(true);
		add(currentJPanel);
		
		updateSize(panel.getPreferredSize());
	}
	
	private void updateSize(Dimension size) {
		setSize(size);
		setResizable(false);
		setVisible(true);
		
		setSize(size.width, size.height + getInsets().top);
		setResizable(false);
		setVisible(true);
	}
	
	
	private class ActionSelectorPanel extends JPanel {
		private Dimension preferredSize;
		
		public ActionSelectorPanel() {
			preferredSize = new Dimension(200, 100);
			setLayout(new GridLayout(3, 1));
			
			add(new JLabel("Select action"));
			add(createAddShapeButton());
			add(createPrintContentButton());
			
		}
		
		private JButton createAddShapeButton() {
			JPanel currentPanel = this;
			JButton addShapesButton = new JButton("Add shape to storage");
			
			addShapesButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (shapeSelectorPanel == null) {
						shapeSelectorPanel = new ShapeSelectorPanel(currentPanel);
					}
					
					setPanel(shapeSelectorPanel);
				}
			});
			
			return addShapesButton;
		}
		
		private JButton createPrintContentButton() {
			JPanel currentPanel = this;
			JButton printContentButton = new JButton("Print storage content");
			
			printContentButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					JPanel nextPanel = null;
					
					try {
						nextPanel = new ContentPanel(currentPanel);
					} catch (EmptyTableException ex) {
						nextPanel = new ErrorMessagePanel(ex.getMessage(), currentPanel);
					} catch (SQLException ex) {
						ex.printStackTrace();
						nextPanel = actionSelectorPanel;
					}
					
					setPanel(nextPanel);
				}
			});
			
			return printContentButton;
		}
		
		@Override
		public Dimension getPreferredSize() {
			return preferredSize;
		}
	}
	
	
	private abstract class BackButtonPanel extends JPanel {
		private JPanel previousPanel;
		private JButton backButton;
		
		public BackButtonPanel(JPanel previousPanel) {
			this.previousPanel = previousPanel;
			backButton = createBackButton();
		}
		
		protected JButton getBackButton() {
			return backButton;
		}
		
		private JButton createBackButton() {
			JButton backButton = new JButton("Back");
			
			backButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					setPanel(previousPanel);
				}
			});
			
			return backButton;
		}
	}
	
	
	private class ShapeSelectorPanel extends BackButtonPanel {
		
		public ShapeSelectorPanel(JPanel previousPanel) {
			super(previousPanel);
			
			setLayout(new GridBagLayout());
			
			setLabel();
			setAddCircleButton();
			setAddRectangleButton();
			setAddTriangleButton();
			setBackButton();
		}
		
		private void setLabel() {
			GridBagConstraints gbc = new GridBagConstraints();
			JLabel jLabel = new JLabel("Select shape");
			
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 0.5;
			gbc.gridx = 0;
			gbc.gridy = 0;
			
			add(jLabel, gbc);
		}
		
		private void setAddCircleButton() {
			JPanel currentPanel = this;
			GridBagConstraints gbc = new GridBagConstraints();
			JButton jButton = new JButton(new ImageIcon("img/circle_0_50x50.png"));
			
			jButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (circleSetterPanel == null) {
						circleSetterPanel = new CircleSetterPanel(currentPanel);
					}
					
					setPanel(circleSetterPanel);
				}
			});
			
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 0.5;
			gbc.gridx = 0;
			gbc.gridy = 1;
			
			add(jButton, gbc);
		}
		
		private void setAddRectangleButton() {
			JPanel currentPanel = this;
			GridBagConstraints gbc = new GridBagConstraints();
			JButton jButton = new JButton(new ImageIcon("img/rectangle_0_68x50.png"));
			
			jButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (rectangleSetterPanel == null) {
						rectangleSetterPanel = new RectangleSetterPanel(currentPanel);
					}
					
					setPanel(rectangleSetterPanel);
				}
			});
			
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 0.5;
			gbc.gridx = 1;
			gbc.gridy = 1;
			
			add(jButton, gbc);
		}
		
		private void setAddTriangleButton() {
			JPanel currentPanel = this;
			GridBagConstraints gbc = new GridBagConstraints();
			JButton jButton = new JButton(new ImageIcon("img/triangle_0_99x50.png"));
			
			jButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (triangleComputationMethodPanel == null) {
						triangleComputationMethodPanel = new TriangleComputationMethodSelectorPanel(currentPanel);
					}
					
					setPanel(triangleComputationMethodPanel);
				}
			});
			
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 0.5;
			gbc.gridx = 2;
			gbc.gridy = 1;
			
			add(jButton, gbc);
		}
		
		private void setBackButton() {
			GridBagConstraints gbc = new GridBagConstraints();
			JButton backButton = getBackButton();
			
			gbc.fill = GridBagConstraints.BOTH;
			gbc.weightx = 0.5;
			gbc.weighty = 0.5;
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbc.gridwidth = 3;
			
			add(backButton, gbc);
		}
	}
	
	private class Request {
		private String label;
		private JTextField textField;
		
		public Request(String label, JTextField textField) {
			this.label = label;
			this.textField = textField;
		}
	}
	
	
	private abstract class SetterPanel extends BackButtonPanel {
		
		public SetterPanel(JPanel previousPanel) {
			super(previousPanel);
			
			setLayout(new GridBagLayout());
			
			int nextY = 0;
			
			nextY = setPicture(nextY);
			nextY = setRequesters(nextY);
			setBackAndSaveButtons(nextY);
		}
		
		private int setPicture(int gridy) {
			ImageIcon image = getPicture();
			GridBagConstraints gbc = new GridBagConstraints();
			
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 0.5;
			gbc.weighty = 0.5;
			gbc.gridx = 0;
			gbc.gridy = gridy;
			gbc.gridwidth = 2;
			gbc.insets = new Insets(5, 5, 5, 5);
			
			add(new JLabel(image), gbc);
			
			return ++gridy;
		}
		
		private int setRequesters(int gridyBegin) {
			Request[] requesters = getRequesters();
			
			int gridy = gridyBegin;
			for (Request req : requesters) {
				gridy = setRequester(gridy, req);
			}
			
			return gridy;
		}

		private int setBackAndSaveButtons(int gridy) {
			GridBagConstraints gbc = new GridBagConstraints();
			
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 0.5;
			gbc.weighty = 0.5;
			gbc.gridy = gridy;
			
			gbc.gridx = 0;
			add(getBackButton(), gbc);
			
			gbc.gridx = 1;
			add(getSaveButton(), gbc);
			
			return ++gridy;
		}
		
		private JButton getSaveButton() {
			JButton saveButton = new JButton("Save");
			
			saveButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					makeAction();
				}
			});
			return saveButton;
		}
		
		private void makeAction() {
			JPanel nextPanel = null;
			
			try {
				saveAction();
				nextPanel = new SuccessMessagePanel("Success", shapeSelectorPanel);
				refresh();
				
			}  catch (NumberFormatException ex) {
				nextPanel = new ErrorMessagePanel("You must enter a real number", this);
			} catch (InvalidArgumentsException ex) {
				nextPanel = new ErrorMessagePanel(ex.getMessage(), this);
			} catch (SQLException ex) {
				ex.printStackTrace();
				nextPanel = actionSelectorPanel;
			}
			
			setPanel(nextPanel);
		}
		
		private int setRequester(int gridy, Request req) {
			GridBagConstraints gbc = new GridBagConstraints();
			
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 0.5;
			gbc.weighty = 0.5;
			gbc.gridy = gridy;
			
			gbc.gridx = 0;
			add(new JLabel(req.label), gbc);
			
			gbc.gridx = 1;
			add(req.textField, gbc);
			
			return ++gridy;
		}
		
		abstract void refresh();
		protected abstract Request[] getRequesters();
		protected abstract ImageIcon getPicture();
		protected abstract void saveAction() throws NumberFormatException,
													InvalidArgumentsException,
													SQLException;
	}
	
	
	private class CircleSetterPanel extends SetterPanel {
		private JTextField radiusTextField;
		
		public CircleSetterPanel(JPanel previousPanel) {
			super(previousPanel);
		}
		
		@Override
		protected void saveAction() throws NumberFormatException,
										   InvalidArgumentsException,
										   SQLException {
			double radius = Double.valueOf(radiusTextField.getText());
			
			shapeStorageService.save(shapeFactory.createCircle(radius));
		}

		@Override
		void refresh() {
			radiusTextField.setText("");
		}

		@Override
		protected Request[] getRequesters() {
			Request radiusRequester = null;
			
			if (radiusTextField == null) {
				radiusTextField = new JTextField();	
			}
			
			radiusRequester = new Request("Radius:", radiusTextField);
			
			return new Request[] { radiusRequester };
		}

		@Override
		protected ImageIcon getPicture() {
			return new ImageIcon("img/circle_1_100x100.png");
		}
	}
	
	
	
	private class RectangleSetterPanel extends SetterPanel {
		private JTextField sideATextField;
		private JTextField sideBTextField;
		
		public RectangleSetterPanel(JPanel previousPanel) {
			super(previousPanel);
		}
		
		@Override
		protected void saveAction() throws NumberFormatException,
										   InvalidArgumentsException,
										   SQLException {
			double sideA = Double.valueOf(sideATextField.getText());
			double sideB = Double.valueOf(sideBTextField.getText());
			
			shapeStorageService.save(shapeFactory.createRectangle(sideA, sideB));
		}

		@Override
		void refresh() {
			sideATextField.setText("");
			sideBTextField.setText("");
		}

		@Override
		protected Request[] getRequesters() {
			Request sideARequester = null;
			Request sideBRequester = null;
			
			if (sideATextField == null) {
				sideATextField = new JTextField();	
			}
			
			if (sideBTextField == null) {
				sideBTextField = new JTextField();	
			}
			
			sideARequester = new Request("Side A:", sideATextField);
			sideBRequester = new Request("Side B:", sideBTextField);
			
			return new Request[] { sideARequester, sideBRequester };
		}

		@Override
		protected ImageIcon getPicture() {
			return new ImageIcon("img/rectangle_1_137x100.png");
		}
	}
	
	
	
	private class TriangleComputationMethodSelectorPanel extends BackButtonPanel {
		
		public TriangleComputationMethodSelectorPanel(JPanel previousPanel) {
			super(previousPanel);
			
			setLayout(new GridLayout(3, 1));
			
			add(getFirstComputationMethodButton());
			add(getSecondComputationMethodButton());
			add(getBackButton());
		}
		
		private JButton getFirstComputationMethodButton() {
			JPanel currentPanel = this;
			JButton jButton = new JButton("Two sides and angle");
			
			jButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (firstMethodComputationPanel == null) {
						firstMethodComputationPanel = new FirstTriangleSetterPanel(currentPanel);
					}
					
					setPanel(firstMethodComputationPanel);
				}
			});
			
			return jButton;
		}
		
		private JButton getSecondComputationMethodButton() {
			JPanel currentPanel = this;
			JButton jButton = new JButton("One side and two angles");
			
			jButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (secondMethodComputationPanel == null) {
						secondMethodComputationPanel = new SecondTriangleSetterPanel(currentPanel);
					}
					
					setPanel(secondMethodComputationPanel);
				}
			});
			
			return jButton;
		}
	}
	
	
	
	private class FirstTriangleSetterPanel extends SetterPanel {
		private JTextField sideATextField;
		private JTextField sideBTextField;
		private JTextField angleABTextField;
		
		public FirstTriangleSetterPanel(JPanel previousPanel) {
			super(previousPanel);
		}
		
		@Override
		protected void saveAction() throws NumberFormatException,
										   InvalidArgumentsException,
										   SQLException {
			double sideA = Double.valueOf(sideATextField.getText());
			double sideB = Double.valueOf(sideBTextField.getText());
			double angleAB = Double.valueOf(angleABTextField.getText());
			
			shapeStorageService.save(shapeFactory.createTriangleBySideSideAngle(sideA, sideB, angleAB));
		}

		@Override
		void refresh() {
			sideATextField.setText("");
			sideBTextField.setText("");
			angleABTextField.setText("");
		}

		@Override
		protected Request[] getRequesters() {
			Request sideARequester = null;
			Request sideBRequester = null;
			Request angleABRequester = null;
			
			if (sideATextField == null) {
				sideATextField = new JTextField();	
			}
			
			if (sideBTextField == null) {
				sideBTextField = new JTextField();	
			}
			
			if (angleABTextField == null) {
				angleABTextField = new JTextField();	
			}
			
			sideARequester = new Request("Side A:", sideATextField);
			sideBRequester = new Request("Side B:", sideBTextField);
			angleABRequester = new Request("Angle α:", angleABTextField);
			
			return new Request[] { sideARequester, sideBRequester, angleABRequester };
		}

		@Override
		protected ImageIcon getPicture() {
			return new ImageIcon("img/triangle_1_169x100.png");
		}
	}
	
	
	private class SecondTriangleSetterPanel extends SetterPanel {
		private JTextField sideATextField;
		private JTextField angleABTextField;
		private JTextField angleACTextField;
		
		public SecondTriangleSetterPanel(JPanel previousPanel) {
			super(previousPanel);
		}
		
		@Override
		protected void saveAction() throws NumberFormatException,
										   InvalidArgumentsException,
										   SQLException {
			double sideA = Double.valueOf(sideATextField.getText());
			double angleAB = Double.valueOf(angleABTextField.getText());
			double angleAC = Double.valueOf(angleACTextField.getText());
			
			shapeStorageService.save(shapeFactory.createTriangleBySideAngleAngle(sideA, angleAB, angleAC));
		}

		@Override
		void refresh() {
			sideATextField.setText("");
			angleABTextField.setText("");
			angleACTextField.setText("");
		}

		@Override
		protected Request[] getRequesters() {
			Request sideARequester = null;
			Request angleABRequester = null;
			Request angleACRequester = null;
			
			if (sideATextField == null) {
				sideATextField = new JTextField();	
			}
			
			if (angleABTextField == null) {
				angleABTextField = new JTextField();	
			}
			
			if (angleACTextField == null) {
				angleACTextField = new JTextField();	
			}
			
			sideARequester = new Request("Side A:", sideATextField);
			angleABRequester = new Request("Angle α:", angleABTextField);
			angleACRequester = new Request("Angle β:", angleACTextField);
			
			return new Request[] { sideARequester, angleABRequester, angleACRequester };
		}

		@Override
		protected ImageIcon getPicture() {
			return new ImageIcon("img/triangle_2_169x100.png");
		}
	}
	
	
	private class ContentPanel extends BackButtonPanel {

		public ContentPanel(JPanel previousPanel) throws SQLException,
														 EmptyTableException {
			super(previousPanel);
			
			setLayout(new GridBagLayout());
			
			setScrollableTable();
			setBackButton();
		}

		private void setScrollableTable() throws SQLException, EmptyTableException {
			JScrollPane scrollableTable = new JScrollPane(getTable());
			GridBagConstraints gbc = new GridBagConstraints();
			
			gbc.fill = GridBagConstraints.BOTH;
			gbc.weightx = 0.5;
			gbc.weighty = 0.5;
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.ipady = 20;

			add(scrollableTable, gbc);
		}
		
		private void setBackButton() {
			JButton backButton = getBackButton();
			GridBagConstraints gbc = new GridBagConstraints();
			
			gbc.fill = GridBagConstraints.BOTH;
			gbc.weightx = 0;
			gbc.weighty = 0;
			gbc.gridx = 0;
			gbc.gridy = 1;

			add(backButton, gbc);
			
		}

		private JTable getTable() throws SQLException, EmptyTableException {
			String[] header = shapeStorageService.getTableHeader();
			String[][] body = shapeStorageService.loadTable();
			
			return new JTable(body, header) {
				private static final long serialVersionUID = 1L;

				@Override
				public boolean isCellEditable(int row, int column) {                
	                return false;
				};
			        
			    @Override
			    public boolean isCellSelected(int row, int column) {                
		            return false;
			    };
			    
			    @Override
			    public boolean isColumnSelected(int column) {
			    	return false;
			    }
			};
		}
	}
	
	
	private class ErrorMessagePanel extends BackButtonPanel {
		public ErrorMessagePanel(String message, JPanel previousPanel) {
			super(previousPanel);
			
			setLayout(new GridLayout(2, 1));
			
			add(new JLabel(message));
			add(getBackButton());
		}
	}
	
	private class SuccessMessagePanel extends JPanel {
		private JPanel nextPanel;
		
		public SuccessMessagePanel(String message, JPanel nextPanel) {
			this.nextPanel = nextPanel;
			
			setLayout(new GridLayout(2, 1));
			
			add(new JLabel(message));
			add(getOKButton(nextPanel));
		}
		
		private JButton getOKButton(JPanel panel) {
			JButton jButton = new JButton("OK");
			
			jButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					makeAction();
				}
			});
			
			return jButton;
		}
		
		private void makeAction() {
			setPanel(nextPanel);
		}
	}
}

