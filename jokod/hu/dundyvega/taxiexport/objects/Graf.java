package hu.dundyvega.taxiexport.objects;

import java.util.ArrayList;

public class Graf {
	
	private Staff root = null;
	private ArrayList<Graf> children = null;
	
	
	public Graf() {
		
	}
	
	public void appendChild(Staff child) {
		
		
		
		//az alfának nincs ága
		
		
		if (root == null) {
		
			root = child;
		
		} else if (children == null) {
			children = new ArrayList<Graf>();

			//System.out.println(child.toString() + "0");
			Graf gr1 = new Graf();
			gr1.appendChild(child);
			children.add(gr1);

			
		} else {
			
			//meg kell nézni, hogy a root-tól való távolság kisebb-e mint a root-tól a gyerekig, a gyerektől az új elemig
			
			boolean kisebb = true;
			
			int rootV = 0;
			double koltseg = 1000000;
			
			for (int i = 0; i < children.size(); ++i) {
				
				if (root.getDifrance(child) + root.getDifrance(children.get(i).getRoot()) >=
						root.getDifrance(children.get(i).getRoot()) + children.get(i).getRoot().getDifrance(child)) {
					
						if (koltseg >= root.getDifrance(children.get(i).getRoot()) + children.get(i).getRoot().getDifrance(child)) {
							
							koltseg = root.getDifrance(children.get(i).getRoot()) + children.get(i).getRoot().getDifrance(child);
							
							rootV = i;
						}
					
				} else if (root.getDifrance(child) + child.getDifrance(children.get(i).getRoot()) < 
						root.getDifrance(children.get(i).getRoot()) + children.get(i).getRoot().getDifrance(child)) {
							
							koltseg = root.getDifrance(child) + child.getDifrance(children.get(i).getRoot());
							kisebb = false;
							rootV = i;
							break;
						}
						
				
			}
			
			
			
			
			if (kisebb) {
				
				
				children.get(rootV).appendChild(child);
				
			} else {
				
				Staff r = children.get(rootV).getRoot();
				children.get(rootV).setRoot(child);
				children.get(rootV).appendChild(r);
			
				
			}
			
		}
		
	}
	
	
	public void tombosit(ArrayList<Staff> staffs) {
		for (int i = 0; i < children.size(); ++i) {
			staffs.add(root);
			
			if (children.get(i).getChildren() != null) {
				children.get(i).tombosit(staffs);
			}
			else {
				staffs.add(children.get(i).getRoot());
			}
		}
	}
	
	
	public void kiirGraf() {
		
		
		
		for (int i = 0; i < children.size(); ++i) {
			System.out.println(root.toString() + " -tól a távolság" + children.get(i).getRoot() + "-ig = " + root.getDifrance(children.get(i).root));
			
			if (children.get(i).getChildren() != null) {
				children.get(i).kiirGraf();
			}
		}
		
	}
	
	public ArrayList<Graf> getChildren() {
		return children;
	}
	
	public Staff getRoot() {
		return root;
	}
	public void setRoot(Staff root) {
		this.root = root;
	}
	

}
