package UI.FileTable;

import java.awt.Component;
import java.io.File;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.TableCellRenderer;

import UI.GlobalDef;

@SuppressWarnings("serial")

public class FileTableCellRenderer extends JLabel implements TableCellRenderer {
	
	FileSystemView fileSystemView = FileSystemView.getFileSystemView();
	
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        this.setFont(new java.awt.Font(GlobalDef.TableFontName, 0, 13));
        setOpaque(true);
        setEnabled(table.isEnabled());
        if (isSelected) {
            this.setBackground(GlobalDef.deepPurple);
            this.setForeground(GlobalDef.loginGray);
        }
        else {
            this.setBackground(table.getBackground());
            this.setForeground(table.getForeground());
        }

//        if (column == 0)  {
//            File file = (File) value;
//            this.setText(fileSystemView.getSystemDisplayName(file));
//            this.setIcon(fileSystemView.getSystemIcon(file));
//        }
//        else if (column == 1) {
//            long datetime = (long)value;
//            SimpleDateFormat sdf = new SimpleDateFormat(" yyyy/MM/dd/ HH:mm:ss");
//            Date date = new Date(datetime);
//            this.setText(sdf.format(date));
//            this.setIcon(null);
//        }
//        else if (column == 2) {
//            String description = (String)value;
//            this.setText(description);
//            this.setIcon(null);
//        } 
//        else if (column == 2) {
//            long size = (long)value;
//            String fileSize = FormetFileSize(size);
//            File file = (File)table.getValueAt(row,0);
//            if (fileSystemView.isComputerNode(file) || fileSystemView.isDrive(file) || file.isDirectory()){
//                this.setText(null);
//            } else {
//                this.setText(fileSize);
//            }
//            this.setIcon(null);
//        }
        this.setText((String)value);
        return this;
    }

    public String FormetFileSize(long fileS) {//转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS == 0){
            return fileSizeString;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) +"G";
        }
        return fileSizeString;
    }
}
