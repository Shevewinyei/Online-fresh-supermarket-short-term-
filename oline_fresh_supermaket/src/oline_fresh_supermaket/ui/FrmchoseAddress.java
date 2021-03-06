package oline_fresh_supermaket.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import oline_fresh_supermaket.control.addrManager;
import oline_fresh_supermaket.model.Beanaddress;
import oline_fresh_supermaket.model.Beanuser;
import oline_fresh_supermaket.util.BaseException;

public class FrmchoseAddress extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	private Button btnSet = new Button("设置为当前送货地址");
	private Button btncancel = new Button("退出");  
	private Object tblTitle[]={"地址编号","省","市","区","具体地址"};
	private Object tblData[][];
	List<Beanaddress> pubs;
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable dataTable=new JTable(tablmod);
	private void reloadTable() {
		try {
			pubs = (new addrManager().loadall(Beanuser.currentLoginUser.getUsr_id()));
			tblData =new Object[pubs.size()][5];
			for(int i=0;i<pubs.size();i++){
				tblData[i][0]=pubs.get(i).getAddr_id()+"";
				tblData[i][1]=pubs.get(i).getAddr_pro();
				tblData[i][2]=pubs.get(i).getAddr_city();
				tblData[i][3]=pubs.get(i).getAddr_area();
				tblData[i][4]=pubs.get(i).getAddr_current();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.dataTable.validate();
			this.dataTable.repaint();
		}catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public FrmchoseAddress() {
		// TODO Auto-generated constructor stub
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnSet);
		toolBar.add(this.btncancel);
		
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		//提取现有数据
		this.reloadTable();
		this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);
		// 屏幕居中显示
		this.setSize(800, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);
		
		this.validate();
		
	
		this.btnSet.addActionListener(this);
		this.btncancel.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == this.btncancel) {
			this.setVisible(false);
		}
		else if(e.getSource()==this.btnSet) {
			int i = FrmchoseAddress.this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "请选择地址", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
			Frm_BuyCar dlg = new Frm_BuyCar(this.pubs.get(i));
			dlg.setVisible(true);
			
		}
	}
	
}
