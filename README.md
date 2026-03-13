# BT_RecycleView - Room Rental Management App

Ứng dụng Android quản lý nhà trọ (MVC + RecyclerView + CRUD + dữ liệu tạm bằng `List`).

## Phân chia công việc

| STT | Thành viên | MSSV | Phần | Công việc chính |
|---:|---|---|---|---|
| 1 | Đoàn Thảo Vân | B22DCCN890 | Read (Hiển thị danh sách) | `MainActivity`, `RecyclerView`, `RoomAdapter`, `item_room.xml` |
| 2 | Nguyễn Nhật Thành | B22DCCN795 | Create (Thêm phòng) | `AddRoomActivity`, form nhập dữ liệu, validate + thêm vào `List` |
| 3 | Phạm Quang Minh | B22DCCN544 | Update (Sửa phòng) | `EditRoomActivity`, load dữ liệu phòng, cập nhật lại `List` |
| 4 | Hoàng Hải Long | B22DCCN496 | Delete (Xóa phòng) | Nhấn giữ item, `AlertDialog` xác nhận, xóa khỏi `List` + cập nhật RecyclerView |
| 5 | Nguyễn Trí Dũng | B22DCCN135 | Search (Tìm kiếm – nâng cao) | Khởi tạo MVC (model/controller/view), tạo model `Room`, `SearchView`, cập nhật RecyclerView |

## Team workflow
- Quy tắc commit/branch/PR: xem `CONTRIBUTING.md`
- Tóm tắt ngắn cho team: xem `docs/TEAM_RULES.md`
- PR template: `.github/pull_request_template.md`

## Notes
- Dữ liệu: chỉ lưu tạm thời bằng `List` (không dùng SQLite/Room Database).

