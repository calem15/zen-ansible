listen {
  port = 4040
  address = "0.0.0.0"
}

namespace "qa_test_nginxlog" {
  format = "$remote_addr - $remote_user [$time_local] \"$request\" $status $body_bytes_sent \"$http_referer\" \"$http_user_agent\" \"$http_x_forwarded_for\" $upstream_response_time $request_time"
  source_files = ["/var/log/nginx/qatest.demo.access.log"]
  labels {
    host = "qa-test.com"
    node = "{{ ansible_fqdn }}"
    cluster = "brand"
  }
}
