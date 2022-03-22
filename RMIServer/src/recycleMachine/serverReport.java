package recycleMachine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.rmi.RemoteException;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class serverReport extends JFrame{

	public static GridLayout bigLayOut=new GridLayout(1, 3);
	
	public static JPanel panel1 = new JPanel();
	public static JPanel panel2 = new JPanel();
//	public static JPanel panel3 = new JPanel();
	
	public serverReport()  throws RemoteException {
		setSize(900,650);
		setLayout(bigLayOut);
		panel1.setLayout(new java.awt.BorderLayout());
		panel2.setLayout(new java.awt.BorderLayout());
//		panel3.setLayout(new java.awt.BorderLayout());
		add(panel1);
		add(panel2);
//		add(panel3);

        loadPieChart(serverClass.pieData());
        loadBarChart(serverClass.barData());
//        loadLineChart(serverClass.barData());
	}
	
	 private void loadPieChart(DefaultPieDataset dpd) {
	        try {

	            JFreeChart chart2 = ChartFactory.createPieChart("Total Transactions By Clients", dpd, true, true, true);
	            PiePlot piePlot = (PiePlot) chart2.getPlot();

	            ChartPanel chartPanel2 = new ChartPanel(chart2);
	            panel1.removeAll();
	            panel1.add(chartPanel2, BorderLayout.CENTER);
	            panel1.validate();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    private void loadBarChart(DefaultCategoryDataset dcd) {
	        try {
	            JFreeChart chart = ChartFactory.createBarChart("Total Transactions Itemswise", null, "Transactions", dcd, PlotOrientation.VERTICAL, false, false, false);
	            CategoryPlot catPlot = chart.getCategoryPlot();
	            catPlot.setRangeGridlinePaint(Color.BLACK);

	            ChartPanel chartPanel = new ChartPanel(chart);
	            panel2.removeAll();
	            panel2.add(chartPanel, BorderLayout.CENTER);
	            panel2.validate();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    private void loadLineChart(DefaultCategoryDataset dcd) {
//	        try {
//	        	DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
//	            dataset.addValue( 15 , "schools" , "1970" );
//	            dataset.addValue( 30 , "schools" , "1980" );
//	            dataset.addValue( 60 , "schools" ,  "1990" );
//	            dataset.addValue( 120 , "schools" , "2000" );
//	            dataset.addValue( 240 , "schools" , "2010" );
//	            dataset.addValue( 300 , "schools" , "2014" );
//	            
//	            dcd=dataset;
//	            JFreeChart chart = ChartFactory.createLineChart("Total Transactions Itemswise", null, "Transactions", dcd, PlotOrientation.VERTICAL, false, false, false);
//	            CategoryPlot catPlot = chart.getCategoryPlot();
//	            catPlot.setRangeGridlinePaint(Color.BLACK);
//
//	            ChartPanel chartPanel = new ChartPanel(chart);
//	            panel3.removeAll();
//	            panel3.add(chartPanel, BorderLayout.CENTER);
//	            panel3.validate();
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        }
	    }
	    
	    public static void main(String args[]) {

			try {
				new serverReport().setVisible(true);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
