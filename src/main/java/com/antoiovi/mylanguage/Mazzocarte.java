package com.antoiovi.mylanguage;

 import java.util.Vector;
/**
 * 
 * @author antoiovi Antonello Iovino
 *
 */
public class Mazzocarte {
int ncarte;
Vector<Integer> mazzo;
public Mazzocarte(int ncarte) throws Exception{
	super();
	this.ncarte = ncarte;
	if(ncarte<=1)
			throw new Exception("Numer must be grater then 1");
	
	mazzo=new Vector<Integer>();
	for(int x=0;x<ncarte;x++){
		mazzo.add(x+1);
	}
	mescola();
}

public void mescola(){
	Vector<Integer> mazzo_source=new Vector<Integer>();
	Vector<Integer> mazzo_dest=new Vector<Integer>();
	mazzo_source.addAll(mazzo);
	do{
	int Min=0;
	int Max=mazzo_source.size()-1;
	int random=Min + (int)(Math.random() * ((Max - Min) + 1));
	Integer val=mazzo_source.get(random);
	mazzo_dest.add(val);
	mazzo_source.remove(random);
	if(mazzo_source.isEmpty())
		break;	
	}while(true);
	mazzo.removeAllElements();
    mazzo.addAll(mazzo_dest);
}

public int getNcarte() {
	return ncarte;
}

public void setNcarte(int ncarte) {
	if(ncarte<=1)
		return;
	this.ncarte = ncarte;
	mazzo.clear();
	//mazzo=new Vector<Integer>();
for(int x=0;x<ncarte;x++){
	mazzo.add(x+1);
}
mescola();	
	
}

public Vector<Integer> getMazzo() {
	return mazzo;
}

public void setMazzo(Vector<Integer> mazzo) {
	this.mazzo = mazzo;
}

public Object[] toArray() {
	return mazzo.toArray();
}



}
