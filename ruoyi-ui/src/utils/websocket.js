class WebSocketClient {
  constructor() {
    this.ws = null
    this.reconnectTimeout = null
    this.messageHandlers = new Map()
    this.isConnected = false
  }

  connect(userId) {
    const wsUrl = `ws://${window.location.hostname}:8080/ws/trading/${userId}`

    this.ws = new WebSocket(wsUrl)

    this.ws.onopen = () => {
      console.log('WebSocket 连接成功')
      this.isConnected = true

      // 清除重连定时器
      if (this.reconnectTimeout) {
        clearTimeout(this.reconnectTimeout)
        this.reconnectTimeout = null
      }
    }

    this.ws.onmessage = (event) => {
      try {
        const message = JSON.parse(event.data)
        console.log('收到 WebSocket 消息：', message)

        // 调用对应类型的处理器
        const handler = this.messageHandlers.get(message.type)
        if (handler) {
          handler(message.data)
        }
      } catch (error) {
        console.error('处理 WebSocket 消息失败：', error)
      }
    }

    this.ws.onerror = (error) => {
      console.error('WebSocket 错误：', error)
    }

    this.ws.onclose = () => {
      console.log('WebSocket 连接关闭')
      this.isConnected = false

      // 5秒后自动重连
      this.reconnectTimeout = setTimeout(() => {
        console.log('尝试重新连接 WebSocket...')
        this.connect(userId)
      }, 5000)
    }
  }

  disconnect() {
    if (this.ws) {
      this.ws.close()
    }
    if (this.reconnectTimeout) {
      clearTimeout(this.reconnectTimeout)
    }
  }

  // 注册消息处理器
  on(type, handler) {
    this.messageHandlers.set(type, handler)
  }

  // 移除消息处理器
  off(type) {
    this.messageHandlers.delete(type)
  }

  // 发送消息
  send(message) {
    if (this.ws && this.isConnected) {
      this.ws.send(JSON.stringify(message))
    }
  }
}

export default new WebSocketClient()
