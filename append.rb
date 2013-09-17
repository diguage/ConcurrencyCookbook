template = File.new("Template.md")
# 
# file = File.open("fileName", "a")
# file.puts ""
def scanFiles(dir) 
  list = []
  Dir.foreach(dir) do |f|
    list << f
  end
  return list
end
# /home/deployer/Java/ConcurrencyCookbook/docs/
scanFiles("/home/deployer/Java/ConcurrencyCookbook/docs/").each do |f|
  template.each do |line|
    f.puts line
  end
end
