Hê lô mình là Phát và đây là project lab 1 của môn Lab211 kì SU26 do thầy ThongNT dạy =))
(link repo Github: https://github.com/tykyfatkie/lab211_lab1_su26)

(Lưu ý: Thư mục out/ chứa các file mã máy .class sau khi compile và đã được thiết lập .gitignore nếu cần).

⚙️ Cách hệ thống hoạt động 
1. Luồng khởi động 
Khi chương trình vừa chạy (qua class Main), các list Students và Mountains sẽ được khởi tạo.

Hệ thống tự động đọc file MountainList.csv để nạp danh sách núi hợp lệ.

Hệ thống tự động tìm và đọc file registrations.dat (bằng ObjectInputStream & EOFException) để khôi phục danh sách sinh viên đã đăng ký từ những lần chạy trước vào bộ nhớ RAM.

2. Xử lý nghiệp vụ trên RAM
Khi người dùng tương tác với Menu (từ Case 1 đến Case 7), mọi thao tác sẽ được xử lý trực tiếp trên RAM để tối ưu tốc độ:

1. New Registration: Nhập sinh viên mới, tự động tính học phí (giảm 35% cho mạng Viettel/VNPT).

2. Update / 4. Delete: Cập nhật hoặc xóa thông tin sinh viên theo MSSV.

3. Display: Hiển thị danh sách hiện tại với định dạng bảng chuẩn xác.

5. Search / 6. Filter: Tìm kiếm theo tên hoặc lọc danh sách theo Campus code (SE, HE, DE, QE, CE).

7. Statistics: Thống kê số lượng sinh viên đăng ký theo từng ngọn núi.

3. Lưu trữ an toàn (Data Persistence)
Cơ chế cờ hiệu (isSaved state): Hệ thống tự động theo dõi xem dữ liệu có bị thay đổi (thêm/sửa/xóa) hay không.

8. Save Data to File: Đóng gói (Serialize) toàn bộ danh sách Students hiện có trên RAM và ghi đè xuống ổ cứng (registrations.dat). Nếu không có sự thay đổi nào, chương trình sẽ báo "No changes" để tiết kiệm tài nguyên I/O.

9. Exit: Trước khi thoát, chương trình sẽ kiểm tra cờ isSaved. Nếu người dùng quên lưu, hệ thống sẽ cảnh báo để tránh mất dữ liệu.

🛠 Công nghệ sử dụng
Ngôn ngữ: Java (Core)

Kỹ thuật: Object-Oriented Programming (OOP), Regular Expressions (Regex).

Lưu trữ: File I/O Streams, Java Serialization (ObjectOutputStream / ObjectInputStream), CSV Parsing.
"""

with open("README.md", "w", encoding="utf-8") as f:
f.write(markdown_content)

Tệp Markdown (`README.md`) của bạn đã sẵn sàng
[file-tag: code-generated-file-0-1778684050233391351]

Mình đã viết xong nội dung file `README.md` dựa theo đúng cấu trúc mã nguồn (tách handler, model, business, tools) và cách hoạt động của hệ thống lưu trữ `.dat` / `.csv` mà chúng ta vừa hoàn thiện. Mình cũng đã thêm luôn thông tin môn học của thầy ThongNT vào phần đầu cho chuẩn bài Lab FPT.

