## order init bean spring
cần hiểu thứ tự khởi động bean :nó sẽ tạo các bean cơ bản "configuration,service,component" như một instant thông qua contructor (nếu có) sau đó nó thêm các dependency là các phụ thuộc cần inject(Spring sẽ inject các dependencies cho bean bằng cách gọi các setter (nếu có) hoặc thông qua các trường được annotate bằng @Autowired (hoặc @Value đối với các thuộc tính).) , sau đó nó gọi phương thức preprocess xử lí rồi postconstruct rồi sẵn sàng sử dụng
## set enviroment on win
setx PRIVATE_KEY_PATH "C:\keys\private_key.pem"
## run on product
```bash
setx CLOUDINARY_URL "cloudinary://345593341212817:ip50kMhMixVv-F6qOjwyKoN5rLk@divcnthxr"
setx EMAIL "ideaswapsp@gmail.com"
setx EMAIL_PASSWORD "vejtkoejstatxska"
setx MONGO_URI "mongodb+srv://nvhai227:abCD%401234@cluster0.kfltces.mongodb.net/?retryWrites=true&w=majority&appName=crud"
java -Dspring.profiles.active=production -jar D:\codevs\springboot\ideaSwap\build\libs\IdeaSwap-0.0.1-SNAPSHOT.jar
```
