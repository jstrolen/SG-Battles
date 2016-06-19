package sg_battles.guns;


public class Shot {
	
	public Guns[] vystrel(int typ, double[] souradnice, double smer) {
		Guns[] zbran=null;
		if (typ==0) {
			zbran=new Guns[1];
			zbran[0]=new Railguns(souradnice, smer);
		}
		
		else if (typ==1) {
			zbran=new Guns[1];
			zbran[0]=new PulseBeam(souradnice, smer);
		}
		
		else if (typ==2) {
			zbran=new Guns[3];
			zbran[0]=new Rocket(new double[]{souradnice[0]+15*Math.cos(smer),
					souradnice[1]+15*Math.sin(smer)}, smer-0.1);
			zbran[1]=new Rocket(new double[]{souradnice[0]+15*Math.cos(smer),
					souradnice[1]+15*Math.sin(smer)}, smer+0.1);
			zbran[2]=new Rocket(new double[]{souradnice[0], souradnice[1]}, smer);
		}
		
		else if (typ==3) {	
			int konst=3;
			zbran=new Guns[6];
			zbran[0]=new Drons(new double[]{souradnice[0]+40*Math.cos(smer), souradnice[1]+40*Math.sin(smer)}, smer);
			smer=smer+Math.PI/konst;
			zbran[1]=new Drons(new double[]{souradnice[0]+40*Math.cos(smer), souradnice[1]+40*Math.sin(smer)}, smer);
			smer=smer+Math.PI/konst;
			zbran[2]=new Drons(new double[]{souradnice[0]+40*Math.cos(smer), souradnice[1]+40*Math.sin(smer)}, smer);
			smer=smer+Math.PI/konst;
			zbran[3]=new Drons(new double[]{souradnice[0]+40*Math.cos(smer), souradnice[1]+40*Math.sin(smer)}, smer);
			smer=smer+Math.PI/konst;
			zbran[4]=new Drons(new double[]{souradnice[0]+40*Math.cos(smer), souradnice[1]+40*Math.sin(smer)}, smer);
			smer=smer+Math.PI/konst;
			zbran[5]=new Drons(new double[]{souradnice[0]+40*Math.cos(smer), souradnice[1]+40*Math.sin(smer)}, smer);
		}
		
		else if (typ==4) {
			zbran=new Guns[2];
			zbran[0]=new WraithGun(new double[]{souradnice[0]+10*Math.cos(smer),
					souradnice[1]+10*Math.sin(smer)}, smer);
			zbran[1]=new WraithGun(new double[]{souradnice[0]-10*Math.cos(smer),
					souradnice[1]-10*Math.sin(smer)}, smer);
		}
		
		else if (typ==5) {
			zbran=new Guns[1];
			zbran[0]=new OriGun(souradnice, smer);
		}
		
		else if (typ==6) {
			zbran=new Guns[1];
			zbran[0]=new JumperDrons(souradnice, smer);
		}
		else if (typ==7) {
			zbran=new Guns[1];
			zbran[0]=new X302Railguns(souradnice, smer);
		}
		else if (typ==8) {
			zbran=new Guns[1];
			zbran[0]=new X302Rocket(souradnice, smer);
		}
		else if (typ==9) {
			zbran=new Guns[2];
			zbran[0]=new DartGun(new double[]{souradnice[0]+5*Math.cos(smer),
					souradnice[1]+5*Math.sin(smer)}, smer);
			zbran[1]=new DartGun(new double[]{souradnice[0]-5*Math.cos(smer),
					souradnice[1]-5*Math.sin(smer)}, smer);
		}
		
		else {
			zbran=new Guns[1];
			zbran[0]=new Railguns(souradnice, smer);
		}

		return zbran;

	}
	
	public Guns vratZbran(int typ) {
		Guns zbran = null;
		if (typ==0) {
			zbran=new Railguns();
		}
		else if (typ==1) {
			zbran=new PulseBeam();
		}
		else if (typ==2) {
			zbran=new Rocket();
		}
		else if (typ==3) {	
			zbran=new Drons();
		}
		else if (typ==4) {
			zbran=new WraithGun();
		}
		else if (typ==5) {
			zbran=new OriGun();
		}
		else if (typ==6) {
			zbran=new JumperDrons();
		}
		else if (typ==7) {
			zbran=new X302Railguns();
		}
		else if (typ==8) {
			zbran=new X302Rocket();
		}
		else if (typ==9) {
			zbran=new DartGun();
		}
		else {
			zbran=new Railguns();
		}
		return zbran;
	}
}
