# A sample Guardfile
# More info at https://github.com/guard/guard#readme

guard :gradle do
  watch(%r{^src/main/(.+)\.*$}) { |m| m[1].split('.')[0].split('/')[-1] }
  watch(%r{^src/test/(.+)\.*$}) { |m| m[1].split('.')[0].split('/')[-1] }
  watch('build.gradle')
end
