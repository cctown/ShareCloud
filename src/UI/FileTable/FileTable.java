package UI.FileTable;

import java.awt.Dimension;

import javax.swing.JTable;

import UI.GlobalDef;

@SuppressWarnings("serial")

public class FileTable extends JTable {
	public FileTable(){
        this.setDefaultRenderer(Object.class, new FileTableCellRenderer());
        this.setAutoCreateRowSorter(true);
        this.getTableHeader().setReorderingAllowed(false);
        this.getTableHeader().setFont(new java.awt.Font(GlobalDef.TableFontName, 0, 13));
        this.setShowHorizontalLines(false);
        this.setShowVerticalLines(false);
        setIntercellSpacing(new Dimension(0,0)); //修改单元格间隔，因此也将影响网格线的粗细。
        setRowMargin(0);//设置相邻两行单元格的距离
    }
}
