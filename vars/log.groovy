def debug(String message) {
  ansiColor('xterm') {
    echo "\033[0;36m${message}\033[0m"
  }
}

def notice(String message) {
  ansiColor('xterm') {
    echo "\033[1;32m${message}\033[0m"
  }
}

def error(String message) {
  ansiColor('xterm') {
    echo "\033[0;31m${message}\033[0m"
  }
}

