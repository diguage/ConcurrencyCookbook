
# 扫描指定目录下到所有文件
def scanFiles(dir) 
  list = []
  Dir.foreach(dir) do |f|
    if f.to_s =~ /\w*md$/ 
      list << f
    end
  end
  return list.sort
end

scanFiles("/home/deployer/Java/ConcurrencyCookbook/docs/").each do |f|
  puts "/home/deployer/Java/ConcurrencyCookbook/docs/#{f.to_s}"

  file = File.open("/home/deployer/Java/ConcurrencyCookbook/docs/#{f.to_s}", "a")
  
  template = File.new("Template.md")
  template.each do |line|
    file.puts line
  end
  file.close
end
