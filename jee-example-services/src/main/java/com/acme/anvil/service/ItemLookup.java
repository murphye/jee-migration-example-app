package com.acme.anvil.service;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.acme.anvil.vo.Item;

public interface ItemLookup extends EJBObject{

	public Item lookupItem(long id) throws RemoteException;
}
