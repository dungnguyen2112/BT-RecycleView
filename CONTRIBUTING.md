# Contributing Guide (BT_RecycleView - Room Rental Management App)

## Quy ước chung
- **Mỗi người làm 1 nhánh riêng**, không code trực tiếp lên `main`.
- **1 PR = 1 chức năng/1 phần được giao** (VD: Search, Create, Update...).
- **Không push file build/IDE** (như `*.iml`, `/.idea/` nếu có).

## Branch naming
Tạo branch theo mẫu:

- `feature/search`
- `feature/create-room`
- `fix/crash-on-submit`
- `chore/update-readme`

## Commit message (Conventional Commits)
Format:

`<type>(<scope>): <mô tả ngắn>`

Ví dụ:
- `feat(search): add SearchView filter rooms`
- `feat(adapter): show room status color`
- `fix(main): prevent crash when query is null`
- `docs(pr): add PR template`
- `chore(gradle): add recyclerview dependency`

**Type gợi ý**:
- `feat`: thêm tính năng
- `fix`: sửa lỗi
- `docs`: tài liệu
- `refactor`: chỉnh code không đổi chức năng
- `chore`: việc lặt vặt (config/deps)

## Pull Request rules
### PR title
Theo mẫu:

`[Part X] <mô tả>`

Ví dụ:
- `[Part 5] Search rooms with SearchView`

### PR description
- Mô tả ngắn bạn làm gì
- Screenshot (nếu có UI)
- Cách test nhanh

### Quy tắc merge
- Prefer **Squash and merge** để lịch sử gọn
- PR phải pass build (mở Android Studio build được)

