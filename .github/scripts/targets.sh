MODRINTH_PROJECT="fiw-warp"
RELEASE_TYPE="release"
DISPLAY_NAME="Fiw Warp"
KOTLIN_RUNTIME="external"
OPTIONAL_LUCKPERMS="false"
TARGETS=(
  "fabric-26.1.2 fabric 26.1.2"
  "fabric fabric 1.21.11"
  "neoforge neoforge 1.21.11"
  "fabric-1.21.8 fabric 1.21.8"
  "neoforge-1.21.8 neoforge 1.21.8"
  "fabric-1.21.1 fabric 1.21.1"
  "neoforge-1.21.1 neoforge 1.21.1"
  "fabric-1.20.1 fabric 1.20.1"
  "forge-1.20.1 forge 1.20.1"
)

jar_path() {
  local module="$1"
  case "$module" in fabric|neoforge) module="$module-1.21.11" ;; esac
  echo "$1/build/libs/fiw-warp-$module-$2.jar"
}
