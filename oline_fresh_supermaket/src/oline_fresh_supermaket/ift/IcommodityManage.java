package oline_fresh_supermaket.ift;

import java.util.List;

import oline_fresh_supermaket.model.BeanFF;
import oline_fresh_supermaket.model.Beancommodity;
import oline_fresh_supermaket.util.BaseException;

public interface IcommodityManage {
	/**
	 *������
	 */
	public List<Beancommodity> loadcommodity()throws BaseException;
	public Beancommodity addcommodity(Beancommodity commodity)throws BaseException;
	public void deletecommodity(int com_id)throws BaseException;
	public Beancommodity seachcommodity(int com_id) throws BaseException;
	public List<Beancommodity> loadall( int ffid)throws BaseException;
	
	public Beancommodity add(String name,double lDprice, int lDcount)throws BaseException;
	public void update(Beancommodity beancommodity)throws BaseException;
	public void Modify(Beancommodity p, int index)throws BaseException;
	public void Modify2(Beancommodity p, double index)throws BaseException;
	public void Modify3(Beancommodity p, double index)throws BaseException;
	public List<Beancommodity> selectName(List<Beancommodity> coms)throws BaseException;
	
}
