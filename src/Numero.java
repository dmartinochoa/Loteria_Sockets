import java.io.Serializable;

public class Numero implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String boleto;
	String premio;

	public Numero(String boleto) {
		this.boleto = boleto;
		this.premio = premio;
	}

	public String getBoleto() {
		return boleto;
	}

	public void setBoleto(String boleto) {
		this.boleto = boleto;
	}

	public String getPremio() {
		return premio;
	}

	public void setPremio(String premio) {
		this.premio = premio;
	}
	@Override
	public String toString() {
		return "El boleto es " + boleto + ", Su premio es " + premio;
	}
}
