package example;
// -*- mode:java; encoding:utf-8 -*-
// vim:set fileencoding=utf-8:
// @homepage@

import java.awt.*;
import java.util.Objects;
import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;

public final class MainPanel extends JPanel {
  private MainPanel() {
    super(new BorderLayout());
    JTextArea textArea = new JTextArea("ComponentPopupMenu Test\naaaaaaaaaaa\nbbbbbbbbbbbbbb\ncccccccccccccc");
    textArea.setComponentPopupMenu(new TextComponentPopupMenu());
    add(new JScrollPane(textArea));
    setPreferredSize(new Dimension(320, 240));
  }

  // // Another way:
  // private static JPopupMenu makePopupMenu() {
  //   JPopupMenu popup = new JPopupMenu();
  //   Action cutAction = new DefaultEditorKit.CutAction();
  //   Action copyAction = new DefaultEditorKit.CopyAction();
  //   Action pasteAction = new DefaultEditorKit.PasteAction();
  //   Action deleteAction = new AbstractAction("delete") {
  //     @Override public void actionPerformed(ActionEvent e) {
  //       ((JTextComponent) getInvoker()).replaceSelection(null);
  //     }
  //   };
  //   Action selectAllAction = new AbstractAction("select all") {
  //     @Override public void actionPerformed(ActionEvent e) {
  //       JPopupMenu p = (JPopupMenu) e.getSource();
  //       ((JTextComponent) p.getInvoker()).selectAll();
  //     }
  //   };
  //   popup.add(cutAction);
  //   popup.add(copyAction);
  //   popup.add(pasteAction);
  //   popup.addSeparator();
  //   popup.add(deleteAction);
  //   popup.addSeparator();
  //   popup.add(selectAllAction);
  //   popup.addPopupMenuListener(new PopupMenuListener() {
  //     @Override public void popupMenuCanceled(PopupMenuEvent e) {}
  //     @Override public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}
  //     @Override public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
  //       JPopupMenu p = (JPopupMenu) e.getSource();
  //       JTextComponent tc = (JTextComponent) p.getInvoker();
  //       boolean hasSelectedText = Objects.nonNull(tc.getSelectedText());
  //       cutAction.setEnabled(hasSelectedText);
  //       copyAction.setEnabled(hasSelectedText);
  //       deleteAction.setEnabled(hasSelectedText);
  //     }
  //   });
  //   return popup;
  // }

  public static void main(String... args) {
    EventQueue.invokeLater(new Runnable() {
      @Override public void run() {
        createAndShowGui();
      }
    });
  }

  public static void createAndShowGui() {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException
         | IllegalAccessException | UnsupportedLookAndFeelException ex) {
      ex.printStackTrace();
    }
    JFrame frame = new JFrame("@title@");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.getContentPane().add(new MainPanel());
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}

class TextComponentPopupMenu extends JPopupMenu {
  private final Action cutAction = new DefaultEditorKit.CutAction();
  private final Action copyAction = new DefaultEditorKit.CopyAction();
  private final Action pasteAction = new DefaultEditorKit.PasteAction();
  private final JMenuItem deleteItem;

  protected TextComponentPopupMenu() {
    super();
    add(cutAction);
    add(copyAction);
    add(pasteAction);
    addSeparator();
    deleteItem = add("delete");
    deleteItem.addActionListener(e -> ((JTextComponent) getInvoker()).replaceSelection(null));
    addSeparator();
    add("select all").addActionListener(e -> ((JTextComponent) getInvoker()).selectAll());
  }

  @Override public void show(Component c, int x, int y) {
    if (c instanceof JTextComponent) {
      JTextComponent tc = (JTextComponent) c;
      boolean hasSelectedText = Objects.nonNull(tc.getSelectedText());
      cutAction.setEnabled(hasSelectedText);
      copyAction.setEnabled(hasSelectedText);
      deleteItem.setEnabled(hasSelectedText);
      super.show(c, x, y);
    }
  }
}
