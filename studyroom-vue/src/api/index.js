import request from '@/utils/request'

// 登录接口
export function login(data) {
  return request({
    url: '/admin/login',
    method: 'post',
    data
  })
}

// 自习室相关接口
export function getRoomList(params) {
  return request({
    url: '/admin/rooms',
    method: 'get',
    params
  })
}

export function addRoom(data) {
  return request({
    url: '/admin/rooms',
    method: 'post',
    data
  })
}

export function updateRoom(id, data) {
  return request({
    url: `/admin/rooms/${id}`,
    method: 'put',
    data
  })
}

export function deleteRoom(id) {
  return request({
    url: `/admin/rooms/${id}`,
    method: 'delete'
  })
}

// 预约管理接口
export function getOrderList(params) {
  return request({
    url: '/LogBill/RootList',
    method: 'get',
    params
  })
}

// 用户管理接口
export function getUserList(params) {
  return request({
    url: '/Kehu/list',
    method: 'get',
    params
  })
}

export function updateUser(data) {
  return request({
    url: '/Kehu/update',
    method: 'put',
    data
  })
}

export function getMbtiList(params) {
  return request({
    url: '/MBTI/list',
    method: 'get',
    params
  })
}

// 楼层管理接口
export function getFloorList(params) {
  return request({
    url: '/StudyRooms/RootList',
    method: 'get',
    params
  })
}

export function floorList(params) {
  return request({
    url: '/StudyRooms/floorList',
    method: 'get',
    params
  })
}

export function addFloor(data) {
  return request({
    url: '/StudyRooms/save',
    method: 'post',
    data
  })
}

export function updateFloor(id, data) {
  return request({
    url: `/StudyRooms/update`,
    method: 'put',
    data
  })
}

export function deleteFloor(id) {
  return request({
    url: `/admin/floors/${id}`,
    method: 'delete'
  })
}

// 座位管理接口
export function getSeatList(params) {
  return request({
    url: `/Seats/RellayList`,
    method: 'get',
    params
  })
}

export function addSeat(data) {
  return request({
    url: '/admin/seats',
    method: 'post',
    data
  })
}

export function updateSeat(id, data) {
  return request({
    url: `/admin/seats/${id}`,
    method: 'put',
    data
  })
}

export function deleteSeat(id) {
  return request({
    url: `/admin/seats/${id}`,
    method: 'delete'
  })
}

// 房间相关接口
export function listRoom(query) {
  return request({
    url: '/room/list',
    method: 'get',
    params: query
  })
}

export function listSeat(params) {
  return request({
    url: '/room/seats',
    method: 'get',
    params: params
  })
}

// 座位相关接口
export function updateSeatPrice(data) {
  return request({
    url: '/Seats/updatePrice',
    method: 'put',
    data
  })
}

export function getSeatRecords(params) {
  return request({
    url: '/Seats/records',
    method: 'get',
    params
  })
}

// 获取日志列表
export function getLogList(params) {
  return request({
    url: '/logbill/list',
    method: 'get',
    params
  })
}

// 报修管理接口
export function getRepairList(params) {
  return request({
    url: '/repair/list',
    method: 'get',
    params
  })
}

export function updateRepairStatus(data) {
  return request({
    url: '/repair/updateStatus',
    method: 'put',
    data
  })
}

export function getRepairDetail(id) {
  return request({
    url: `/repair/detail/${id}`,
    method: 'get'
  })
}

export function addRepairRecord(data) {
  return request({
    url: '/repair/addRecord',
    method: 'post',
    data
  })
} 