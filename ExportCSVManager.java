import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.io.*;


public class ExportCSVManager
{
    public boolean exportToCSV(JTable tableToExport, String pathToExportTo)
    {

        try {

            String name= pathToExportTo+".csv";
            TableModel model = tableToExport.getModel();
            FileWriter csv = new FileWriter(name);
            System.out.println(model.getRowCount());

            for (int i = 0; i < model.getColumnCount(); i++)
            {
                csv.write(model.getColumnName(i) + ";");

            }

            csv.write("\n");

            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++)
                {
                    if(model.getValueAt(i,j)!=null)
                    {
                        csv.write(model.getValueAt(i, j).toString() + ";");
                    }
                    else
                    {
                        csv.write(";");
                    }
                }
                csv.write("\n");
            }

            csv.close();
            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public DefaultTableModel importCSV(JTable table,String name)
    {
        File file = new File(name);
        DefaultTableModel model = new DefaultTableModel();

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String firstLine = br.readLine().trim();
            String[] columnsName = firstLine.split(";");

            model.setColumnIdentifiers(columnsName);
            table.setModel(model);

            Object[] tableLines = br.lines().toArray();
            for(int i=0;i<tableLines.length;i++)
            {

                String line = tableLines[i].toString().trim();
                String[] dataRow = line.split(";");
                model.addRow(dataRow);

            }
        }
        catch (FileNotFoundException ex)
        {
            JOptionPane.showMessageDialog(table,"File not found, cannot load data");
        }
        catch (IOException ex)
        {
            JOptionPane.showMessageDialog(table,"Cannot open file");
        }

        return model;
    }


}
