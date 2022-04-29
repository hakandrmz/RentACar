# Turkcell Geleceği Yazanlar Programı

# Araba Kiralama Sistemi Projesi

** # Proje adresi: [hakandurmaz.guru](https://hakandurmazrentacarproject.herokuapp.com/swagger-ui/index.html#/) #  **

## Gereksinimler

* Brand, Color nesneleri -> id,name,ListAll,CRUD,getById,Marka ismi ve renk ismi tekrar edemez.

* Car-> id, dailyPrice, modelYear, description, brand, colorList, CRUD,getById,Araba listelenirken ->brandname,
  colorname

* Sisteme result mimarisini ekle. Eski kodları buna göre refactor et.

* Arabaları sayfalı listeleyiniz. Arabaları günlük fiyatları üzerinden seçime göre artan ve düşen sıralayınız.

* CarMaintenance-> id, description, returnDate,CRUD,getByCarId

* Global exception handler advice-> validation, BusinessException

* Bakımda olan araba kiraya verilemez.

* Kirada olan araba bakıma gönderilemez.

* Arabalar kiralanırken müşteriler ek hizmetler satın alabilmelidir.-> bebek koltuğu, scooter vs.AdditionalService->id,
  name, dailyPrice

* OrderedAdditionalService yap.

* Arabalar kiralanırken farklı bir şehire bırakılabilir. Eğer böyle olursa kiralamaya 750 tl eklenir.

* Kurumsal müşteriler araba kiralayabilmelidirler. Kiralama kuralları bireysel müşteri ile aynıdır. Corporate customer->
  vergiNo, şirketismi , email …

* Individual customer-> id, firstname, lastname,tckimlikno

* Tüm kiralamalar sonucunda fatura kesilmelidir. (faturano, oluşturmatarihi, kiralama tarihleri, toplam kiralama gün
  sayısı, kiralama tutarı, müşteri) Belirli tarih aralığında tüm faturalar listelenebilmeli. Müşteriye ait faturalar
  listelenebilmeli.

* Müşteriler aynı zamanda kullanıcıdır.(id, email, password)

* Arabalara güncel kilometre bilgisi eklenmelidir. Kiralamalarda başlangıç kilometresi girilmelidir. Kiralama
  dönüşlerinde dönüş kilometresi bilgisi girilmelidir. Bu bilgi arabada da güncellenmelidir.

* Arabaya ait hasarlar girilebilmelidir. (id, carid, hasarbilgisi)

* Pos servisi ekleyiniz. Servis olumsuz döndüğünde kiralama gerçekleşmemelidir. (payments, rental, addiservice, ınvoice)

* Yapılan ödemeleri bir tabloda tutunuz. Bir ödeme en fazla bir kere alınmalıdır.

* Magic stringlerden kurtulunuz.

* Müşteriler kart bilgilerini kaydetmek isterse kart bilgileri ayrı bir tabloda tutulmalıdır.

* Müşteriler arabayı belirtilenden geç teslim ederse kiralama günü kadar yeni ödeme alınır. Fark tutarı kadar yeni
  fatura kesilir. Bu kurallar ek hizmetler için de geçerlidir.

